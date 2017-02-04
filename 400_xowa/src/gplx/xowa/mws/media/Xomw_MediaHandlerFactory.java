/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mws.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_MediaHandlerFactory {
//		/**
//		* Default, MediaWiki core media handlers
//		*
//		* @var array
//		*/
//		private static $coreHandlers = [
//			'image/jpeg' => JpegHandler::class,
//			'image/png' => PNGHandler::class,
//			'image/gif' => GIFHandler::class,
//			'image/tiff' => TiffHandler::class,
//			'image/webp' => WebPHandler::class,
//			'image/x-ms-bmp' => BmpHandler::class,
//			'image/x-bmp' => BmpHandler::class,
//			'image/x-xcf' => XCFHandler::class,
//			'image/svg+xml' => SvgHandler::class, // official
//			'image/svg' => SvgHandler::class, // compat
//			'image/vnd.djvu' => DjVuHandler::class, // official
//			'image/x.djvu' => DjVuHandler::class, // compat
//			'image/x-djvu' => DjVuHandler::class, // compat
//		];
//
//		/**
//		* @var array
//		*/
//		private $registry;
//
//		/**
//		* Instance cache of MediaHandler objects by mimetype
//		*
//		* @var MediaHandler[]
//		*/
//		private $handlers;
//
//		public function __construct( array $registry ) {
//			$this->registry = $registry + self::$coreHandlers;
//		}
//
//		protected function getHandlerClass( $type ) {
//			if ( isset( $this->registry[$type] ) ) {
//				return $this->registry[$type];
//			} else {
//				return false;
//			}
//		}
//
//		/**
//		* @param String $type mimetype
//		* @return boolean|MediaHandler
//		*/
//		public function getHandler( $type ) {
//			if ( isset( $this->handlers[$type] ) ) {
//				return $this->handlers[$type];
//			}
//
//			$class = $this->getHandlerClass( $type );
//			if ( $class !== false ) {
//				/** @var MediaHandler $handler */
//				$handler = new $class;
//				if ( !$handler->isEnabled() ) {
//					wfDebug( __METHOD__ . ": $class is not enabled\n" );
//					$handler = false;
//				}
//			} else {
//				wfDebug( __METHOD__ . ": no handler found for $type.\n" );
//				$handler = false;
//			}
//
//			$this->handlers[$type] = $handler;
//			return $handler;
//		}
}
