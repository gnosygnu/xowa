( function ( mw, $ ) {
	var $wpbBannerImageContainer = $( '.wpb-topbanner' ),
		$img = $( 'img.wpb-banner-image' );

	function positionBanner( $container ) {
		/**
		 * Javascript to fine tune position of banner according to position coordinates.
		 */
		// extract position parameters
		var maxOffsetTop, offsetTop, maxOffsetLeft, offsetLeft,
			minOffsetTop = 0,
			minOffsetLeft = 0,
			$wpbBannerImage = $container.find( '.wpb-banner-image' ),
			totalOffsetX = 0,
			totalOffsetY = 0,
			centerX = $wpbBannerImage.data( 'pos-x' ),
			centerY = $wpbBannerImage.data( 'pos-y' );
		// reset translations applied by css
		$wpbBannerImage.css( {
			transform: 'translate(0)',
			MozTransform: 'translate(0)',
			WebkitTransform: 'translate(0)',
			msTransform: 'translate(0)',
			'margin-left': 0,
			'margin-top': 0
		} );
		// Adjust vertical focus
		if ( $wpbBannerImage.height() > $container.height() ) {
			// this is the max shift up that can be achieved without leaving blank space below
			maxOffsetTop = $wpbBannerImage.height() -
				$container.height();
			// offset beyond center 0
			offsetTop = centerY * $wpbBannerImage.height() / 2;
			// offset for default center is maxOffsetTop/2
			// total offset = offset for center + manual offset
			totalOffsetY = maxOffsetTop / 2 + offsetTop;
			// shift the banner no more than maxOffsets on either side
			if ( totalOffsetY > maxOffsetTop ) {
				totalOffsetY = maxOffsetTop;
			} else if ( totalOffsetY < minOffsetTop ) {
				totalOffsetY = minOffsetTop;
			}
		}
		// Adjust horizontal focus
		if ( $wpbBannerImage.width() > $container.width() ) {
			// this is the max shift that can be achieved without leaving blank space
			maxOffsetLeft = $wpbBannerImage.width() -
				$container.width();
			// offset beyond center 0
			offsetLeft = centerX * $wpbBannerImage.width() / 2;
			// offset for default center is maxOffsetLeft/2
			// total offset = offset for center + manual offset
			totalOffsetX = maxOffsetLeft / 2 + offsetLeft;
			// shift the banner no more than maxOffsets on either side
			if ( totalOffsetX > maxOffsetLeft ) {
				totalOffsetX = maxOffsetLeft;
			} else if ( totalOffsetX < minOffsetLeft ) {
				totalOffsetX = minOffsetLeft;
			}
		}
		// shift the banner horizontally and vertically by the offsets calculated above
		$wpbBannerImage.css( {
			'margin-top': -totalOffsetY,
			'margin-left': -totalOffsetX
		} );
	}
	$( window ).on( 'resize', $.debounce(
			100,
			function () {
				positionBanner( $wpbBannerImageContainer );
			}
		) );
	// set focus after image has loaded
	$img.load( function () {
		positionBanner( $wpbBannerImageContainer );
	} );
	// Image might be cached
	if ( $img.length && $img[ 0 ].complete ) {
		positionBanner( $wpbBannerImageContainer );
	}
	// Expose interface for testing.
	mw.wpb = {
		positionBanner: positionBanner
	};
}( mediaWiki, jQuery ) );
