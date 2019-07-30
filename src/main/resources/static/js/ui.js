$(function(){
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
		scrollTop > 305 ? $('.quick_menu').addClass('active') : $('.quick_menu').removeClass('active');
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



// Sub menu open/close
function menuToggle (_this) {
	var _this= $(_this);
	if (_this.parents(".sub_haeder").hasClass("open")) {
		_this.parents(".sub_haeder").removeClass("open").addClass("close");
		_this.parents("body").find(".sub_contents").removeClass("active");
	}else {
		_this.parents(".sub_haeder").removeClass("close").addClass("open");
		_this.parents("body").find(".sub_contents").addClass("active");
	}
}


// Mobile gnb open/close
function gnbOpen (_this) {
	var _this= $(_this);
	_this.parents(".header_wrap").stop().find(".gnb_wrap").stop().animate({right:"0"},200);
	_this.parents(".header_wrap").find(".opacity_bg").show();
	$('body').addClass('scrollOff').on('scroll touchmove mousewheel', function(e){
      e.preventDefault();
   });
  $("body").css({overflow:'hidden'}).bind('touchmove', function(e){e.preventDefault()});
}
function gnbClose (_this) {
	var _this= $(_this);
	_this.parents(".header_wrap").stop().find(".gnb_wrap").animate({right:"-240px"},200);
	_this.parents(".header_wrap").find(".opacity_bg").hide();
	 $('body').removeClass('scrollOff').off('scroll touchmove mousewheel');
	$("body").css({overflow:'visible'}).unbind('touchmove');
}
function menuOpen (_this) {
	var _this = $(_this);
	if (_this.parent().hasClass("on")) {
		_this.parents(".depth1").find(">li").removeClass("on");
		_this.parent().find(".depth2").stop().slideUp(200);
	}else {
		_this.parents(".depth1").find(">li").removeClass("on");
		_this.parents(".depth1").find(".depth2").stop().slideUp(200);
		_this.parent().addClass("on");
		_this.parent().find(".depth2").stop().slideDown(200);
	}
}
function menuOpen2 (_this) {
	var _this = $(_this);
	if (_this.parent().hasClass("on")) {
		_this.parents(".depth2").find("ul>li").removeClass("on");
		_this.parent().find(".depth3").stop().slideUp(200);
	}else {
		_this.parents(".depth2").find("ul>li").removeClass("on");
		_this.parents(".depth2").find(".depth3").stop().slideUp(200);
		_this.parent().addClass("on");
		_this.parent().find(".depth3").stop().slideDown(200);
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
	popup.addClass("active");
}
function popClose (_this) {
	var _this = $(_this);
	_this.parents(".popup_wrap").hide();
	_this.parents(".popup_wrap").removeClass("active");
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


// 사이트맵
function sitemapShow (_this) {
	var _this = $(_this);
	_this.parents("html").find(".site_map_wrap").show();
  $("body").css({overflow:'hidden'});
}
function sitemapHide (_this) {
	var _this = $(_this);
	_this.parents(".site_map_wrap").hide();
	$("body").css({overflow:'visible'});
}


// 통합검색
function viewSearch (_this) {
	var _this = $(_this);
	if (_this.hasClass("active")) {
		_this.parents("html").find(".total_search").hide();
		_this.removeClass("active");
		_this.parents("html").find(".header_wrap .gnb_wrap").stop().animate({right:"-240px"},200);
		_this.parents("html").find(".header_wrap .opacity_bg").hide();
	}else {
		_this.parents("html").find(".total_search").show();
		_this.addClass("active");
		_this.parents("html").find(".header_wrap .gnb_wrap").stop().animate({right:"-240px"},200);
		_this.parents("html").find(".header_wrap .opacity_bg").hide();
	}
}
function closeSearch (_this) {
	var _this = $(_this);
		_this.parents("html").find(".total_search").hide();
		_this.parents("html").find(".header_wrap .gnb_wrap").stop().animate({right:"-240px"},200);
		_this.parents("html").find(".header_wrap .gnb_wrap .btn_search").removeClass("active");
		_this.parents("html").find(".header_wrap .opacity_bg").hide();
}


// Family site
function selectFunc2 (_this) {
	var _this = $(_this);
	_this.next("ul").stop().toggle();
}
function selectClick2 (_this) {
	var _this = $(_this);
	_this.parents(".select_box ul").prev().html(_this.find("a").html());
	_this.parents(".select_box ul").stop().hide();
}