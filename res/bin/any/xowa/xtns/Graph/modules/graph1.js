( function ( $, mw ) {
//	mw.hook( 'wikipage.content' ).add( function ( $content ) {
		var specs = {} // mw.config.get( 'wgGraphSpecs' );

		vg.data.load.sanitizeUrl = function () {
//			mw.log.warn( 'Vega 1.x does not allow external URLs. Switch to Vega 2.' );
			return false;
		};

		if ( specs ) {
      var $content = $('#content'); // XOWA
			$content.find( '.mw-graph.mw-graph-always' ).each( function () {
				var graphId = $( this ).data( 'graph-id' ),
					el = this;
				if ( !specs[ graphId ] ) {
//					mw.log.warn( graphId );
				} else {
					vg.parse.spec( specs[ graphId ], function ( chart ) {
						if ( chart ) {
							chart( { el: el } ).update();
						}
					} );
				}
			} );
		}
//	} );
}( jQuery, null ) );
