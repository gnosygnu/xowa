( function ( $, mw ) {

	mw.hook( 'wikipage.content' ).add( function ( $content ) {

		/**
		 * Replace a graph image by the vega graph.
		 *
		 * If dependencies aren't loaded yet, they are loaded first
		 * before rendering the graph.
		 *
		 * @param {jQuery} $el Graph container.
		 */
		function loadAndReplaceWithGraph( $el ) {
			// TODO, Performance BUG: loading vega and calling api should happen in parallel
			// Lazy loading dependencies
			mw.loader.using( 'ext.graph.vega2', function () {
				new mw.Api().get( {
					formatversion: 2,
					action: 'graph',
					title: mw.config.get( 'wgPageName' ),
					hash: $el.data( 'graphId' )
				} ).done( function ( data ) {
					mw.drawVegaGraph( $el[ 0 ], data.graph, function ( error ) {
						var $layover = $el.find( '.mw-graph-layover' );
						if ( !error ) {
							$el.find( 'img' ).remove();
							$layover.remove();
						} else {
							mw.log.warn( error );
						}
						$el.removeClass( 'mw-graph-interactable' );
						// TODO: handle error by showing some message
					} );
				} );
			} );
		}

		// Make graph containers clickable
		$content.find( '.mw-graph.mw-graph-interactable' ).on( 'click', function () {
			var $this = $( this ),
				$button = $this.find( '.mw-graph-switch' );

			// Prevent multiple clicks
			$this.off( 'click' );

			// Add a class to decorate loading
			$button.addClass( 'mw-graph-loading' );

			// Replace the image with the graph
			loadAndReplaceWithGraph( $this );
		} );

	} );

}( jQuery, mediaWiki ) );
