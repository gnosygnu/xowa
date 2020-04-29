/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes;
// MW.SRC:1.33.1

import gplx.xowa.mediawiki.*;
/*
TODO:
* $wgHooks
* array_shift
* Closure?
*/

/**
 * Hooks class.
 *
 * Used to supersede $wgHooks, because globals are EVIL.
 *
 * @since 1.18
 */
public class XomwHooks {
    /**
     * Array of events mapped to an array of callbacks to be run
     * when that event is triggered.
     */
    protected static XophpArray handlers = XophpArray.New();

    /**
     * Attach an event handler to a given hook.
     *
     * @param string $name Name of hook
     * @param callable $callback Callback function to attach
     *
     * @since 1.18
     */
    public static void register(String name, XophpCallable callback) {
        handlers.Get_by_ary(name).Add(callback);
    }

    /**
     * Clears hooks registered via Hooks::register(). Does not touch $wgHooks.
     * This is intended for use while testing and will fail if MW_PHPUNIT_TEST is not defined.
     *
     * @param string $name The name of the hook to clear.
     *
     * @since 1.21
     * @throws MWException If not in testing mode.
     * @codeCoverageIgnore
     */
    public static void clear(String name) {
        // if (!defined('MW_PHPUNIT_TEST') && !defined('MW_PARSER_TEST')) {
        //    throw new MWException('Cannot reset hooks in operation.');
        // }

        XophpArray_.unset(handlers, name);
    }

    /**
     * Returns true if a hook has a function registered to it.
     * The function may have been registered either via Hooks::register or in $wgHooks.
     *
     * @since 1.18
     *
     * @param string $name Name of hook
     * @return bool True if the hook has a function registered to it
     */
    public static boolean isRegistered(String name) {
//        global $wgHooks;
//        return !XophpArray_.empty($wgHooks[$name]) || !XophpArray_.empty(handlers[$name]);
        return false;
    }

    /**
     * Returns an array of all the event functions attached to a hook
     * This combines functions registered via Hooks::register and with $wgHooks.
     *
     * @since 1.18
     *
     * @param string $name Name of the hook
     * @return array
     */
    public static XophpArray getHandlers(String name) {
//        global $wgHooks;

        if (!isRegistered(name)) {
            return XophpArray.New();
//        } else if (!XophpArray_.isset(handlers, name)) {
//            return $wgHooks[name];
//        } else if (!isset($wgHooks[name])) {
//            return handlers[name];
        } else {
//            return XophpArray_.array_merge(handlers.Get_by_ary(name), $wgHooks[name]);
            return XophpArray_.array_merge(handlers.Get_by_ary(name));
        }
    }

    /**
     * @param string $event Event name
     * @param array|callable hook
     * @param array $args Array of parameters passed to hook functions
     * @param string|null $deprecatedVersion [optional]
     * @param string &$fname [optional] Readable name of hook [returned]
     * @return null|string|bool
     */
    private static String callHook(String event, Object hookObj, XophpArray args) {return callHook(event, hookObj, args, null, null);}
    private static String callHook(String event, Object hookObj, XophpArray args, String deprecatedVersion) {return callHook(event, hookObj, args, deprecatedVersion, null);}
    private static String callHook(String event, Object hookObj, XophpArray args, String deprecatedVersion,
		String fname
   ) {
        XophpArray hook;
        // Turn non-array values into an array. (Can't use casting because of objects.)
        if (!XophpArray_.is_array(hookObj)) {
            hook = XophpArray.New(hookObj);
        }
        else { // XO: cast it to XophpArray
            hook = (XophpArray)hookObj;
        }

//        if (!array_filter(hook)) {
            // Either array is empty or it's an array filled with null/false/empty.
//            return null;
//        }

//        if (is_array(hook[0])) {
            // First element is an array, meaning the developer intended
            // the first element to be a callback. Merge it in so that
            // processing can be uniform.
//            hook = array_merge(hook[0], array_slice(hook, 1));
//        }

        /**
         * hook can be: a function, an object, an array of $function and
         * $data, an array of just a function, an array of object and
         * method, or an array of object, method, and data.
         */
        if (XophpType_.instance_of(hook.Get_at(0), XophpCallable.class)) { // XophpClosure
//            $fname = "hook-" + event + "-closure";
//            $callback = array_shift(hook);
        } else if (XophpObject_.is_object(hook.Get_at_str(0))) {
//            Object object = XophpArray_.array_shift(hook);
//            Object method = XophpArray_.array_shift(hook);

            // If no method was specified, default to on$event.
//            if ($method === null) {
//                $method = "on" + event;
//            }

//            $fname = get_class($object) . '::' . $method;
//            $callback = [ $object, $method ];
//        } else if (is_string(hook[0])) {
//            $fname = $callback = array_shift(hook);
//        } else {
//            throw new MWException('Unknown datatype in hooks for ' . $event . "\n");
        }

        // Run autoloader (workaround for call_user_func_array bug)
        // and throw error if not callable.
//        if (!is_callable($callback)) {
//            throw new MWException('Invalid callback ' . $fname . ' in hooks for ' . $event . "\n");
//        }

        // mark hook as deprecated, if deprecation version is specified
        if (deprecatedVersion != null) {
//            wfDeprecated("$event hook (used in $fname)", deprecatedVersion);
        }

        XophpCallable callback = null;

        // Call the hook.
        XophpArray hook_args = XophpArray_.array_merge(hook, args);
        return (String)XophpCallable_.call_user_func_array(callback, hook_args);
    }
    /**
     * Call hook functions defined in Hooks::register and $wgHooks.
     *
     * For the given hook event, fetch the array of hook events and
     * process them. Determine the proper callback for each hook and
     * then call the actual hook using the appropriate arguments.
     * Finally, process the return value and return/throw accordingly.
     *
     * For hook event that are not abortable through a handler's return value,
     * use runWithoutAbort() instead.
     *
     * @param string $event Event name
     * @param array $args Array of parameters passed to hook functions
     * @param string|null $deprecatedVersion [optional] Mark hook as deprecated with version number
     * @return bool True if no handler aborted the hook
     *
     * @throws Exception
     * @throws FatalError
     * @throws MWException
     * @since 1.22 A hook function is not required to return a value for
     *   processing to continue. Not returning a value (or explicitly
     *   returning null) is equivalent to returning true.
     */
    public static boolean run(String event) {return run(event, XophpArray.New(), null);}
    public static boolean run(String event, XophpArray args) {return run(event, args, null);}
    public static boolean run(String event, XophpArray args, String deprecatedVersion) {
        XophpArray handlers = getHandlers(event);
        for (int i = 0; i < handlers.count(); i++) {
            XophpCallable hook = (XophpCallable)handlers.Get_at(i);
            Object retval = callHook(event, hook, args, deprecatedVersion);
            if (retval == null) {
                continue;
            }

            // Process the return value.
            if (XophpString_.is_string(retval)) {
                // String returned means error.
                throw new XophpFatalError((String)retval);
            } else if (XophpObject_.is_false(retval)) {
                // False was returned. Stop processing, but no error.
                return false;
            }
        }

        return true;
    }

//    /**
//     * Call hook functions defined in Hooks::register and $wgHooks.
//     *
//     * @param string $event Event name
//     * @param array $args Array of parameters passed to hook functions
//     * @param string|null $deprecatedVersion [optional] Mark hook as deprecated with version number
//     * @return bool Always true
//     * @throws MWException If a callback is invalid, unknown
//     * @throws UnexpectedValueException If a callback returns an abort value.
//     * @since 1.30
//     */
//    public static function runWithoutAbort($event, array $args = [], $deprecatedVersion = null) {
//        foreach (getHandlers($event) as hook) {
//            $fname = null;
//            retval = callHook($event, hook, $args, $deprecatedVersion, $fname);
//            if (retval !== null && retval !== true) {
//                throw new UnexpectedValueException("Invalid return from $fname for unabortable $event.");
//            }
//        }
//        return true;
//    }
}
