/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
/**
* Hooks cl+ass.
*
* Used to supersede $wgHooks, because globals are EVIL.
*
* @since 1.18
*/
public class XomwHooks {
//		/**
//		* Array of events mapped to an array of callbacks to be run
//		* when that event is triggered.
//		*/
//		protected static $handlers = [];
//
//		/**
//		* Attach an event handler to a given hook.
//		*
//		* @param String $name Name of hook
//		* @param callable $callback Callback function to attach
//		*
//		* @since 1.18
//		*/
//		public static function register( $name, $callback ) {
//			if ( !isset( self::$handlers[$name] ) ) {
//				self::$handlers[$name] = [];
//			}
//
//			self::$handlers[$name][] = $callback;
//		}
//
//		/**
//		* Clears hooks registered via Hooks::register(). Does not touch $wgHooks.
//		* This is intended for use while testing and will fail if MW_PHPUNIT_TEST is not defined.
//		*
//		* @param String $name The name of the hook to clear.
//		*
//		* @since 1.21
//		* @throws MWException If not in testing mode.
//		*/
//		public static function clear( $name ) {
//			if ( !defined( 'MW_PHPUNIT_TEST' ) && !defined( 'MW_PARSER_TEST' ) ) {
//				throw new MWException( 'Cannot reset hooks in operation.' );
//			}
//
//			unset( self::$handlers[$name] );
//		}
//
//		/**
//		* Returns true if a hook has a function registered to it.
//		* The function may have been registered either via Hooks::register or in $wgHooks.
//		*
//		* @since 1.18
//		*
//		* @param String $name Name of hook
//		* @return boolean True if the hook has a function registered to it
//		*/
//		public static function isRegistered( $name ) {
//			global $wgHooks;
//			return !empty( $wgHooks[$name] ) || !empty( self::$handlers[$name] );
//		}
//
//		/**
//		* Returns an array of all the event functions attached to a hook
//		* This combines functions registered via Hooks::register and with $wgHooks.
//		*
//		* @since 1.18
//		*
//		* @param String $name Name of the hook
//		* @return array
//		*/
//		public static function getHandlers( $name ) {
//			global $wgHooks;
//
//			if ( !self::isRegistered( $name ) ) {
//				return [];
//			} elseif ( !isset( self::$handlers[$name] ) ) {
//				return $wgHooks[$name];
//			} elseif ( !isset( $wgHooks[$name] ) ) {
//				return self::$handlers[$name];
//			} else {
//				return array_merge( self::$handlers[$name], $wgHooks[$name] );
//			}
//		}
//
//		/**
//		* Call hook functions defined in Hooks::register and $wgHooks.
//		*
//		* For a certain hook event, fetch the array of hook events and
//		* process them. Determine the proper callback for each hook and
//		* then call the actual hook using the appropriate arguments.
//		* Finally, process the return value and return/throw accordingly.
//		*
//		* @param String $event Event name
//		* @param array $args Array of parameters passed to hook functions
//		* @param String|null $deprecatedVersion Optionally, mark hook as deprecated with version number
//		* @return boolean True if no handler aborted the hook
//		*
//		* @throws Exception
//		* @throws FatalError
//		* @throws MWException
//		* @since 1.22 A hook function is not required to return a value for
//		*   processing to continue. Not returning a value (or explicitly
//		*   returning null) is equivalent to returning true.
//		*/
//		public static function run( $event, array $args = [], $deprecatedVersion = null ) {
//			foreach ( self::getHandlers( $event ) as $hook ) {
//				// Turn non-array values into an array. (Can't use casting because of objects.)
//				if ( !is_array( $hook ) ) {
//					$hook = [ $hook ];
//				}
//
//				if ( !array_filter( $hook ) ) {
//					// Either array is empty or it's an array filled with null/false/empty.
//					continue;
//				} elseif ( is_array( $hook[0] ) ) {
//					// First element is an array, meaning the developer intended
//					// the first element to be a callback. Merge it in so that
//					// processing can be uniform.
//					$hook = array_merge( $hook[0], array_slice( $hook, 1 ) );
//				}
//
//				/**
//				* $hook can be: a function, an Object, an array of $function and
//				* $data, an array of just a function, an array of Object and
//				* method, or an array of Object, method, and data.
//				*/
//				if ( $hook[0] instanceof Closure ) {
//					$func = "hook-$event-closure";
//					$callback = array_shift( $hook );
//				} elseif ( is_object( $hook[0] ) ) {
//					$Object = array_shift( $hook );
//					$method = array_shift( $hook );
//
//					// If no method was specified, default to on$event.
//					if ( $method === null ) {
//						$method = "on$event";
//					}
//
//					$func = get_class( $Object ) . '::' . $method;
//					$callback = [ $Object, $method ];
//				} elseif ( is_string( $hook[0] ) ) {
//					$func = $callback = array_shift( $hook );
//				} else {
//					throw new MWException( 'Unknown datatype in hooks for ' . $event . "\n" );
//				}
//
//				// Run autoloader (workaround for call_user_func_array bug)
//				// and throw error if not callable.
//				if ( !is_callable( $callback ) ) {
//					throw new MWException( 'Invalid callback ' . $func . ' in hooks for ' . $event . "\n" );
//				}
//
//				// mark hook as deprecated, if deprecation version is specified
//				if ( $deprecatedVersion !== null ) {
//					wfDeprecated( "$event hook (used in $func)", $deprecatedVersion );
//				}
//
//				// Call the hook.
//				$hook_args = array_merge( $hook, $args );
//				$retval = call_user_func_array( $callback, $hook_args );
//
//				// Process the return value.
//				if ( is_string( $retval ) ) {
//					// String returned means error.
//					throw new FatalError( $retval );
//				} elseif ( $retval === false ) {
//					// False was returned. Stop processing, but no error.
//					return false;
//				}
//			}
//
//			return true;
//		}
}
