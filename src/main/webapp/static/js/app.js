const paiso = {
	ratesApiUrl: location.protocol + '//' + location.hostname + (location.port ? ':'+location.port: '') + '/exchange',
	userApiUrl: location.protocol + '//' + location.hostname + (location.port ? ':'+location.port: '') + '/user',
	loggedInUser: {email:'', recentInquiries: []},
	init: function() {

		paiso.setupUserProfile();
		paiso.fetchRecentInquiries();		
		paiso.setupExchangeRateDatePicker();
		
		$('.currency-dropdown li a').click(function(event) {
			const selection = $(this).text();
			const currency = paiso.toCurrency(selection);
			$(this).parents('.currency.dropdown').find('.dropdown-toggle').html(selection + ' <span class="caret"></span>');
			paiso.refreshExchangeRate(currency);
			
			const recent = paiso.loggedInUser.recentInquiries;
			let effectiveDate = $('.datepicker').val();
			if(!effectiveDate) effectiveDate = moment().format("YYYY-MM-DD");
			
			recent.unshift({currency: currency.code, date: effectiveDate});
			
			if(recent.length > 10)
				recent.pop();
			
			paiso.refreshRecentInquiries();
		});
	},
	
	setupUserProfile: function() {
		const userEmailNode = $('.user-email');
		if(userEmailNode.length > 0)
			paiso.loggedInUser.email = $(userEmailNode).text();		
	},
	
	setupExchangeRateDatePicker: function() {
		paiso.exchangeDatePicker = $('.date').datepicker({
		    format: 'yyyy-mm-dd',
		    endDate: '0d',
		    autoclose: true,
		    todayHighlight: true,
		    todayBtn: "linked"
		});
		
		$('.form-control.datepicker').val(moment().format("YYYY-MM-DD"));
	},
	
	findExchangeRate: function(currencyCode) {
		const dateStr = $('.datepicker').val();
		const utcDateStr = moment(dateStr, 'YYYY-MM-DD').utc().format('YYYY-MM-DD');
		const ratesUrl = effectiveDate
							? `${paiso.ratesApiUrl}/${effectiveDate}/${currencyCode}`
							: `${paiso.ratesApiUrl}/${currencyCode}`;
		
		return fetch(ratesUrl, {
			credentials: 'include'
		});
	},
	
	findRecentUserInquiries: function() {
		const userApiUrl = `${paiso.userApiUrl}/${paiso.loggedInUser.email}/recent-inquiries`;

		return fetch(userApiUrl, {
			credentials: 'include'
		});
	},
	
	refreshExchangeRate: function(currency) {
		paiso.findExchangeRate(currency.code)
		.then(function(response){
			if(response.ok) {
				response.json().then(function(json) {
					let tableBody = '';
					Object.keys(json.exchangeRate).forEach(function(key){
						tableBody += `<tr><td>${key}</td><td>${json.exchangeRate[key]}</td></tr>`
					});
					
					const exchangeMsgNode = $('.exchange-rate-msg');
					exchangeMsgNode.show();
					$('.activeCurrency', exchangeMsgNode).html(currency.text);
					$('.exchange-rate-table-body').html(tableBody);
				});
			}
		});
	},
	
	fetchRecentInquiries: function() {
		paiso.findRecentUserInquiries()
		.then(function(response){
			if(response.ok){
				response.json().then(function(json){
					paiso.loggedInUser.recentInquiries = json;
					paiso.refreshRecentInquiries();
				});
			}
		});		
	},
	
	refreshRecentInquiries: function() {
		let tableBody = '';
		paiso.loggedInUser.recentInquiries.forEach(function(inquiry){
			tableBody += `<tr><td>${inquiry.currency}</td><td>${inquiry.date}</td></tr>`
		});
		
		const recentInquiryTable = $('.recent-inquiry-container');
		recentInquiryTable.show();
		$('.recent-inquiries-table-body').html(tableBody);
	},
	
	toCurrency: function(currency) {
		if(currency.length > 6) {
			return {
				code : currency.substring(0,3),
				text: currency.substring(6)
			};
		}else return currency;
	}
};

$(function() {
	paiso.init();
});
