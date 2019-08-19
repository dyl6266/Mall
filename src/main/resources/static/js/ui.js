$(function(){
	// Go top
	$(".go_top").click( function(){
	  $("html, body").animate({ scrollTop : 0 }, 500);
	  return false;
	});


	// Gnb
	$(".gnb_wrap .depth1 > li").hover(function(){
		$(".header_wrap").addClass("active");
	});
	$(".gnb").mouseleave(function(){
		$(".header_wrap").removeClass("active");
	});


	// Sticky menu
	$(window).scroll(function(){
		var scrollTop = $(document).scrollTop();
		scrollTop > 1 ? $(".go_top").addClass("active") : $(".go_top").removeClass("active");
	});


	// 화면사이즈 변화감지
	$( window ).resize(function() {
		var windowWidth = $( window ).width();
			if(windowWidth < 1000) {
				$(".header_wrap").removeClass("web_header");
			} else {
				$(".header_wrap").addClass("web_header");
			}
	}).resize();
});


// Mobile gnb open/close
function gnbOpen (_this) {
	var _this= $(_this);
	$(".gnb").stop().animate({right:"0"},200);
	$("header").find(".gnb_bg").show();
}
function gnbClose (_this) {
	var _this= $(_this);
	$(".gnb").stop().animate({right:"-280px"},200);
	$("header").find(".gnb_bg").hide();
}

function tabFunc (_this, _thisId) {
	var _this= $(_this);
	var _thisId = $(_thisId);
	_this.parent().find(".active").removeClass("active");
	_this.addClass("active");
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


// Active
function addActive (_this) {
	var _this = $(_this);
	if(_this.hasClass("active")) {
		_this.parent().find("> .active").removeClass("active");
		_this.removeClass("active");
	} else {
		_this.parent().find("> .active").removeClass("active");
		_this.addClass("active");
	}
}