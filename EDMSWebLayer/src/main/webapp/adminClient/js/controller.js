
$(document).on('click', '.delete_btn', function() {
	var user = $(this);
	loadUsers();
	
	$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/user/' + $(this).attr('user_id'),
	    type: 'DELETE',
	    dataType: "json",
	    success: function(result) {
	    	loadUsers();
	    	alert('Пользователь ' + $(user.parent().siblings('.login').get(0)).text() + ' успешно удалён');
	    }
	});
});

function validateUserForm() {
	var userRegexp = /^[a-zA-Z0-9_-]{3,15}$/;
	var fioRegexp = /^[a-zA-Zа-яА-Я\s]+$/;
	
	if(!userRegexp.test($('#user_login').val())) {
		$('#user_errors').text('Имя пользователя  не соответствует требованиям.');
		return false;
	}
	if(!userRegexp.test($('#user_password').val())) {
		$('#user_errors').text('Пароль не соответствует требованиям.');
		return false;
	}
	
	if(!fioRegexp.test($('#user_fio').val())) {
		$('#user_errors').text('ФИО не соответствует требованиям.');
		return false;
	}
	return true;
}

function createOrUpdateUser() {
	var user = {};
	user.userId = $('#user_id').val(); 
	user.userFio = $('#user_fio').val();
	user.userLogin = $('#user_login').val();
	user.userPassword = $('#user_password').val();
	
	

	
	var roles = $('.user_role:checked');
	if(roles.length > 0) {
		user.roles = [];
		for(var i = 0; i < roles.length; i++) {
			user.roles.push({
				roleId : $(roles[i]).val(),
				roleName: ''
			});
		}
		
	}
	if(validateUserForm()) {
		$.ajax({
		    url: 'http://localhost:8080/EDMSWebLayer/rest/user/',// + $(this).attr('user_id'),
		    type: 'POST',
		    contentType: 'application/json',
		    data: JSON.stringify(user),
		    success: function(result) {
		    	loadUsers();
		    	alert('Пользователь успешно добавлен');
		    	$('#user_dialog').dialog('close');
		    },
		    error: function(result) {
		    	alert('Ошибка сервера; ' + result.responseText);
		    }
		});
	}


	
}

$(document).on('click', '.edit_btn', function() {
	var userId = $(this).attr('user_id');
	$('#user_dialog').dialog('open');
	initEditUserDialog(userId);
	
});

function initEditUserDialog(userId) {
	initUserDialog();
	
	$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/user/'+ userId,
	    type: 'GET',
	    success: function(result) {
	    	var user = result;
	    	
	    	
	    	$('#user_id').val(user.userId); 
	    	$('#user_fio').val(user.userFio);
	    	$('#user_login').val(user.userLogin);
	    	$('#user_password').val(user.userPassword);
	    	
	    	var roles = user.roles;
	    	if(roles.length > 0) {
	    		for(var i = 0; i < roles.length; i++) {
		    		if($('#role_' + roles[i].roleId).length > 0) {
		    			$('#role_' + roles[i].roleId).prop('checked', true);
		    		}
		    	}	
	    	}
	    	/*$('#role_list').empty();
	    	var roles = result;
	    	var rolesComp = '';
	    	for(var i = 0; i < roles.length; i++) {
	    		var role = roles[i];
	    		rolesComp += '<div>';
	    		
	    		rolesComp += '<input type = "checkbox" value="' + role.roleId + '" class="user_role" id="role_' + role.roleId + '">'+
	    		'<span>' + role.roleName + '</span>';
	    		
	    		rolesComp += '</div>';
	    	}
	    	$('#role_list').append(rolesComp);*/
	    }
	});

	
	
}

function initUserDialog() {
	
	$('#user_errors').text('');
	$('#user_login').val('');
	$('#user_fio').val('');
	$('#user_password').val('');
	$('.user_role').prop('checked', false);
	
	$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/role/getAll',
	    type: 'GET',
	    success: function(result) {
	    	$('#role_list').empty();
	    	var roles = result;
	    	var rolesComp = '';
	    	for(var i = 0; i < roles.length; i++) {
	    		var role = roles[i];
	    		rolesComp += '<div>';
	    		
	    		rolesComp += '<input type = "checkbox" value="' + role.roleId + '" class="user_role" id="role_' + role.roleId + '">'+
	    		'<span>' + role.roleName + '</span>';
	    		
	    		rolesComp += '</div>';
	    	}
	    	$('#role_list').append(rolesComp);
	    }
	});
};


$(document).on('click', '#add_user', function() {
	initUserDialog();
	$('#user_dialog').dialog('open');
	
});

function loadUsers() {
	$('#show_users').show();
	$('.user_row').remove();
	/*$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/user/getAll',
	    type: 'GET',
	    success: function(result) {
	    	
	    	if(!!result) {
	    		var usersTable = '';
	    		var users = result;
	    		for(var i = 0; i < users.length; i++) {
	    			usersTable += '' + 
	    				'<tr class="user_row">' +
	    			 		'<td class="login">' + users[i].userLogin + '</td>' +
	    			 		'<td class="fio">' + users[i].userFio + '</td>' +
	    			 		'<td class="buttons">' +
	    			 			'<button class="delete_btn" user_id=' + users[i].userId + ' id="delete_' + users[i].userId + '">Удалить</button>' +
	    			 			'<button class="edit_btn" user_id=' + users[i].userId + ' id="edit_' + users[i].userId + '">Редактировать</button>' +
	    			 		'</td>' +
	    			 	'</tr>';
	    		}
	    		$('#user_list').append(usersTable);
	    	}
	    	$('#mainDiv').text(result);
	    }
	});*/
	
	$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/user/multiget',
	    contentType: 'application/json',
	    type: 'POST',
	    data: JSON.stringify(multigetParams),
	    success: function(result) {
	    	
	    	if(!!result) {
	    		var usersTable = '';
	    		var users = result;
	    		for(var i = 0; i < users.length; i++) {
	    			usersTable += '' + 
	    				'<tr class="user_row">' +
	    			 		'<td class="login">' + users[i].userLogin + '</td>' +
	    			 		'<td class="fio">' + users[i].userFio + '</td>' +
	    			 		'<td class="buttons">' +
	    			 			'<button class="delete_btn" user_id=' + users[i].userId + ' id="delete_' + users[i].userId + '">Удалить</button>' +
	    			 			'<button class="edit_btn" user_id=' + users[i].userId + ' id="edit_' + users[i].userId + '">Редактировать</button>' +
	    			 		'</td>' +
	    			 	'</tr>';
	    		}
	    		$('#user_list').append(usersTable);
	    	}
	    	$('#mainDiv').text(result);
	    }
	});
}

var multigetParams = {
	pageNum: 1,
	recsOnPage: 5
}
var lastPageNum;
var allRowsCount;

function initPager() {
	var rowsCount;
	
	$.ajax({
	    url: 'http://localhost:8080/EDMSWebLayer/rest/user/getRowsCount',
	    type: 'GET',
	    success: function(data) {
	    	
	    	rowsCount = data;
	    	allRowsCount = rowsCount; 
	    	var pagesCount = Math.ceil(rowsCount/multigetParams.recsOnPage);
	    	lastPageNum = pagesCount;
	    	$('.pageSection').text('Страница: ' + multigetParams.pageNum);
	    	$('.pagesSection').append('Всего страниц: ' + pagesCount); 
	    	$('.rowsSection').append('Всего пользователей: ' + rowsCount); 
	    	
	    }
	});
}


$(document).on('click', '.firstPage', function() {
	if(lastPageNum <= 1) {
		return;
	}
	multigetParams.pageNum = 1;
	$('.pageSection').text('Страница: ' + multigetParams.pageNum);
	loadUsers();
});

$(document).on('click', '.lastPage', function() {
	if(lastPageNum <= 1) {
		return;
	}
	multigetParams.pageNum = lastPageNum;
	$('.pageSection').text('Страница: ' + multigetParams.pageNum);
	loadUsers();
});

$(document).on('click', '.prevPage', function() {
	if(multigetParams.pageNum > 1) {
		multigetParams.pageNum--;
		$('.pageSection').text('Страница: ' + multigetParams.pageNum);
		loadUsers();
	}
});

$(document).on('change', '.rowsOnPage', function() {
	multigetParams.recsOnPage = $('.rowsOnPage').val();
	var pagesCount = Math.ceil(allRowsCount/multigetParams.recsOnPage);
	lastPageNum = pagesCount;
	$('.pagesSection').text('Всего страниц: ' + pagesCount); 
	loadUsers();
});

$(document).on('click', '.nextPage', function() {
	if(multigetParams.pageNum < lastPageNum) {
		multigetParams.pageNum++;
		$('.pageSection').text('Страница: ' + multigetParams.pageNum);
		loadUsers();
	}
});
$(function() {
	$('#user_dialog').dialog(
		{
			autoOpen : false,
			title: 'Добавление нового пользователя.',
			buttons: 
				[
			         {
			            text: "OK",
			            click: createOrUpdateUser
			         }, 
			         {
				            text: "Отмена",
				            click: function() {
				              $( this ).dialog( "close" );
				            }
				      }
			     ]
		}
	);
	initPager();
	$('#mainDiv').css('color', 'red');
	loadUsers();
	
});
