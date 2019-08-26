$(function(){
	// Shortcuts
	$(".shortcut > a").on("focus", function(){
		$(this).css({ height : "45px", lineHeight : "45px" });
	});

	$(".shortcut > a").on("blur", function(){
		$(this).css({ height : '0' });
	});


	// Go top
	$(".go_top").click( function(){
	  $("html, body").animate({ scrollTop : 0 }, 200);
	  return false;
	});
});
	

// Left open/close
function menuOpen (_this) {
	var _this = $(_this);
	if (_this.parent().hasClass("on")) {
		_this.closest("ul").find("li").removeClass("on");
		_this.closest("ul").find("ul").stop().slideUp(200);
	}else {
		_this.closest("ul").find("li").removeClass("on");
		_this.closest("ul").find("ul").stop().slideUp(200);
		_this.parent().addClass("on");
		_this.parent().find("ul").stop().slideDown(200);
	}
}


// Accodian
function accodianFunc (_this) {
	var _this = $(_this);
	if (_this.closest("li").hasClass("active")) {
		_this.closest("ul").find("li").removeClass("active");
		_this.closest("ul").find("li .text").stop().slideUp(200);
	}else {
		_this.closest("ul").find("li").removeClass("active");
		_this.closest("ul").find("li .text").stop().slideUp(200);
		_this.closest("li").addClass("active");
		_this.closest("li").find(".text").stop().slideDown(200);
	}
}


// Tab
function tabFunc (_this, _thisId) {
	var _this= $(_this);
	var _thisId = $(_thisId);
	_this.closest("ul").find(".active").removeClass("active");
	_this.parent().addClass("active");
	_this.parents(".tab_wrap").find(".tab_cont").hide();
	$(_thisId).show();
}


// Popup
function popOpen (popup) {
	var popup = $(popup);
	popup.show();
}
function popClose (_this) {
	var _this = $(_this);
	_this.parents(".popup_wrap").hide();
}


// Select
function selectFunc (_this) {
	var _this = $(_this);
	_this.next("ul").stop().slideToggle(250);
}
function selectClick (_this) {
	var _this = $(_this);
	_this.parents(".select_box ul").prev().html(_this.find("a").html());
	_this.parents(".select_box ul").stop().slideUp(250);
}


// Tooltip 
function tooltipFunc (_this) {
	var _this = $(_this);
	_this.find(".tooltip").toggle();
}


// Add class
function addfunc (_this) {
	var _this = $(_this);
	_this.toggleClass("active");
}


// Delete
function delFunc (_this) {
	var _this = $(_this);
	_this.parent().remove();
}


// Sticky menu
$(window).scroll(function(){
	var scrollTop = $(document).scrollTop();
	scrollTop > 510 ? $('#quick_menu').addClass('active') : $('#quick_menu').removeClass('active');
});
