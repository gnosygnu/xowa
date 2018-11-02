(function($) {
	$.fn.tabber = function() {
		return this.each(function() {
			// create tabs
			var $this = $(this),
			    tabContent = $this.children('.tabbertab'),
			    nav = $('<ul>').addClass('tabbernav');
			tabContent.each(function() {
				var anchor = $('<a>').text(this.title).attr('title', this.title).attr('href', 'javascript:void(0);');
				$('<li>').append(anchor).appendTo(nav);
			});
			$this.prepend(nav);

			/**
			 * Internal helper function for showing content
			 * @param  string title to show, matching only 1 tab
			 * @return true if matching tab could be shown
			 */
			function showContent(title) {
				var content = tabContent.filter('[title="' + title + '"]');
				if (content.length !== 1) return false;
				tabContent.hide();
				content.show();
				nav.find('.tabberactive').removeClass('tabberactive');
				nav.find('a[title="' + title + '"]').parent().addClass('tabberactive');
				return true;
			}
			// setup initial state
			var loc = location.hash.replace('#', '');
			if ( loc == '' || !showContent(loc) ) {
        var active_tabs = tabContent.filter('[data-active="true"]');
        var initial_tab = null;
        if (active_tabs.length == 0) {
          initial_tab = tabContent.first().attr('title');
        }
        else {
          initial_tab = active_tabs.attr('title');
        }
				showContent(initial_tab);
			}

			// Repond to clicks on the nav tabs
			nav.on('click', 'a', function(e) {
				var title = $(this).attr('title');
				e.preventDefault();
				location.hash = '#' + title;
				showContent( title );
			});

			$this.addClass('tabberlive');
		});
	};
})(jQuery);

$(document).ready(function() {
	$('.tabber').tabber();
});
