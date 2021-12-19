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

package gplx.xowa.mediawiki.vendor.wikimedia.Assert.src;

import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpObject_;
import gplx.xowa.mediawiki.XophpType;
import gplx.xowa.mediawiki.XophpType_;

// MW.SRC:1.33.1
/**
 * Assert provides functions for assorting preconditions (such as parameter types) and
 * postconditions. It is intended as a safer alternative to PHP's assert() function.
 *
 * Note that assertions evaluate expressions and add function calls, so using assertions
 * may have a negative impact on performance when used in performance hotspots. The idea
 * if this class is to have a neat tool for assertions if and when they are needed.
 * It is not recommended to place assertions all over the code indiscriminately.
 *
 * For more information, see the the README file.
 *
 * @license MIT
 * @author Daniel Kinzler
 * @copyright Wikimedia Deutschland e.V.
 */
public class XomwAssert {

    /**
     * Checks a precondition, that is, throws a PreconditionException if condition is false.
     * For checking call parameters, use Assert::parameter() instead.
     *
     * This is provided for completeness, most preconditions should be covered by
     * Assert::parameter() and related assertions.
     *
     * @see parameter()
     *
     * @note This is intended mostly for checking preconditions in constructors and setters,
     * or before using parameters in complex computations.
     * Checking preconditions in every function call is not recommended, since it may have a
     * negative impact on performance.
     *
     * @param bool condition
     * @param string description The message to include in the exception if the condition fails.
     *
     * @throws PreconditionException if condition is not true.
     */
    public static void precondition(boolean condition, String description) {
        if (!condition) {
            throw new XomwPreconditionException("Precondition failed: {0}", description);
        }
    }

    /**
     * Checks a parameter, that is, throws a ParameterAssertionException if condition is false.
     * This is similar to Assert::precondition().
     *
     * @note This is intended for checking parameters in constructors and setters.
     * Checking parameters in every function call is not recommended, since it may have a
     * negative impact on performance.
     *
     * @param bool condition
     * @param string name The name of the parameter that was checked.
     * @param string description The message to include in the exception if the condition fails.
     *
     * @throws ParameterAssertionException if condition is not true.
     */
    public static void parameter(boolean condition, String name, String description) {
        if (!condition) {
            throw new XomwParameterAssertionException(name, description);
        }
    }

    /**
     * Checks an parameter's type, that is, throws a InvalidArgumentException if condition is false.
     * This is really a special case of Assert::precondition().
     *
     * @note This is intended for checking parameters in constructors and setters.
     * Checking parameters in every function call is not recommended, since it may have a
     * negative impact on performance.
     *
     * @note If possible, type hints should be used instead of calling this function.
     * It is intended for cases where type hints to not work, e.g. for checking primitive types.
     *
     * @param string type The parameter's expected type. Can be the name of a native type or a
     *        class or interface. If multiple types are allowed, they can be given separated by
     *        a pipe character ("|").
     * @param mixed value The parameter's actual value.
     * @param string name The name of the parameter that was checked.
     *
     * @throws ParameterTypeException if value is not of type (or, for objects, is not an
     *         instance of) type.
     */
    public static void parameterType(Class type, Object value, String name) {
        // if (!hasType(value, XophpArray.explode('|', type))) {
        if (!hasType(value, type)) {
            throw new XomwParameterTypeException(name, XophpType_.To_str(type));
        }
    }

    /**
     * Checks the type of all elements of an parameter, assuming the parameter is an array,
     * that is, throws a ParameterElementTypeException if value
     *
     * @note This is intended for checking parameters in constructors and setters.
     * Checking parameters in every function call is not recommended, since it may have a
     * negative impact on performance.
     *
     * @param string type The elements' expected type. Can be the name of a native type or a
     *        class or interface. If multiple types are allowed, they can be given separated by
     *        a pipe character ("|").
     * @param mixed value The parameter's actual value. If this is not an array,
     *        a ParameterTypeException is raised.
     * @param string name The name of the parameter that was checked.
     *
     * @throws ParameterTypeException If value is not an array.
     * @throws ParameterElementTypeException If an element of value  is not of type
     *         (or, for objects, is not an instance of) type.
     */
    public static void parameterElementType(Class type, XophpArray<Class> value, String name) {
        // parameterType(XophpArray.class, valueObj, name);

        // allowedTypes = explode('|', type);

        for (Object element : value) {
            if (!hasType(element, type)) {
                throw new XomwParameterElementTypeException(name, XophpType_.To_str(type));
            }
        }
    }

    /**
     * Checks a postcondition, that is, throws a PostconditionException if condition is false.
     * This is very similar Assert::invariant() but is intended for use only after a computation
     * is complete.
     *
     * @note This is intended for sanity-checks in the implementation of complex algorithms.
     * Note however that it should not be used in performance hotspots, since evaluating
     * condition and calling postcondition() costs time.
     *
     * @param bool condition
     * @param string description The message to include in the exception if the condition fails.
     *
     * @throws PostconditionException
     */
    public static void postcondition(boolean condition, String description) {
        if (!condition) {
            throw new XomwPostconditionException("Postcondition failed: {0}", description);
        }
    }

    /**
     * Checks an invariant, that is, throws a InvariantException if condition is false.
     * This is very similar Assert::postcondition() but is intended for use throughout the code.
     *
     * @note This is intended for sanity-checks in the implementation of complex algorithms.
     * Note however that it should not be used in performance hotspots, since evaluating
     * condition and calling postcondition() costs time.
     *
     * @param bool condition
     * @param string description The message to include in the exception if the condition fails.
     *
     * @throws InvariantException
     */
    public static void invariant(boolean condition, String description) {
        if (!condition) {
            throw new XomwInvariantException("Invariant failed: {0}", description);
        }
    }

    /**
     * @param mixed value
     * @param array allowedTypes
     *
     * @return bool
     */
    private static boolean hasType(Object value, Class allowedTypes) {
        // Apply strtolower because gettype returns "NULL" for null values.
        //type = strtolower(gettype(value));
        //
        //if (in_array(type, allowedTypes)) {
        //    return true;
        //}
        //
        //if (is_callable(value) && in_array('callable', allowedTypes)) {
        //    return true;
        //}

        if (XophpObject_.is_object(value) && isInstanceOf(value, allowedTypes)) {
            return true;
        }

        return false;
    }

    /**
     * @param mixed value
     * @param array allowedTypes
     *
     * @return bool
     */
    private static boolean isInstanceOf(Object value, Class type) {return XophpType_.instance_of(value, type);}
    private static boolean isInstanceOf(Object value, XophpArray<Class> allowedTypes) {
        for (Class type : allowedTypes) {
            if (XophpType_.instance_of(value, type)) {
                return true;
            }
        }

        return false;
    }

}
