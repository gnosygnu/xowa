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
package gplx.xowa.mediawiki.includes.libs.services;

import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpCallback;
import gplx.xowa.mediawiki.XophpObject_;
import gplx.xowa.mediawiki.XophpType_;
/*
XOTODO:
* array_diff: https://www.php.net/manual/en/function.array-diff.php
* array_diff_key: https://www.php.net/manual/en/function.array-diff-key
* XomwAssert: /vendor/wikimedia/Assert/src
*/
// MW.SRC:1.33.1
/**
 * ServiceContainer provides a generic service to manage named services using
 * lazy instantiation based on instantiator callback functions.
 *
 * Services managed by an instance of ServiceContainer may or may not implement
 * a common interface.
 *
 * @note When using ServiceContainer to manage a set of services, consider
 * creating a wrapper or a subclass that provides access to the services via
 * getter methods with more meaningful names and more specific return type
 * declarations.
 *
 * @see docs/injection.txt for an overview of using dependency injection in the
 *      MediaWiki code base.
 */
public class XomwServiceContainer implements XomwDestructibleService {

    /**
     * @var object[]
     */
    private XophpArray<Object> services = new XophpArray();

    /**
     * @var callable[]
     */
    private XophpArray<XophpCallback> serviceInstantiators = new XophpArray();

    /**
     * @var callable[][]
     */
    private XophpArray<XophpArray<XophpCallback>> serviceManipulators = new XophpArray();

    /**
     * @var bool[] disabled status, per service name
     */
    private XophpArray<Boolean> disabled = new XophpArray();

    /**
     * @var array
     */
    private XophpArray extraInstantiationParams;

    /**
     * @var bool
     */
    private boolean destroyed = false;

    /**
     * @param array $extraInstantiationParams Any additional parameters to be passed to the
     * instantiator function when creating a service. This is typically used to provide
     * access to additional ServiceContainers or Config objects.
     */
    public XomwServiceContainer() {this(new XophpArray());}
    public XomwServiceContainer(XophpArray extraInstantiationParams) {
        this.extraInstantiationParams = extraInstantiationParams;
    }

    /**
     * Destroys all contained service instances that implement the DestructibleService
     * interface. This will render all services obtained from this ServiceContainer
     * instance unusable. In particular, this will disable access to the storage backend
     * via any of these services. Any future call to getService() will throw an exception.
     *
     * @see resetGlobalInstance()
     */
    public void destroy() {
        for (String name : this.getServiceNames()) {
            Object service = this.peekService(name);
            if (service != null && XophpType_.instance_of(service, XomwDestructibleService.class)) {
                ((XomwDestructibleService)service).destroy();
            }
        }

        // Break circular references due to the this reference in closures, by
        // erasing the instantiator array. This allows the ServiceContainer to
        // be deleted when it goes out of scope.
        this.serviceInstantiators = new XophpArray();
        // Also remove the services themselves, to avoid confusion.
        this.services = new XophpArray();
        this.destroyed = true;
    }

    /**
     * @param array $wiringFiles A list of PHP files to load wiring information from.
     * Each file is loaded using PHP's include mechanism. Each file is expected to
     * return an associative array that maps service names to instantiator functions.
     */
    public void loadWiringFiles(XophpArray wiringFiles) {
//        foreach ($wiringFiles as $file) {
//            // the wiring file is required to return an array of instantiators.
//            $wiring = require $file;
//
//            // Assert::postcondition(
//            //    is_array($wiring),
//            //    "Wiring file $file is expected to return an array!"
//            // );
//
//            this.applyWiring($wiring);
//        }
    }

    /**
     * Registers multiple services (aka a "wiring").
     *
     * @param array $serviceInstantiators An associative array mapping service names to
     *        instantiator functions.
     */
    public void applyWiring(XophpArray $serviceInstantiators) {
        // Assert::parameterElementType('callable', $serviceInstantiators, '$serviceInstantiators');

//        foreach ($serviceInstantiators as $name => $instantiator) {
//            this.defineService($name, $instantiator);
//        }
    }

    /**
     * Imports all wiring defined in $container. Wiring defined in $container
     * will override any wiring already defined locally. However, already
     * existing service instances will be preserved.
     *
     * @since 1.28
     *
     * @param ServiceContainer $container
     * @param string[] $skip A list of service names to skip during import
     */
    public void importWiring(XomwServiceContainer container) {this.importWiring(container, new XophpArray<>());}
    public void importWiring(XomwServiceContainer container, XophpArray<String> skip) {
//        XophpArray<String> newInstantiators = XophpArray.array_diff_key(
//            container.serviceInstantiators,
//            XophpArray.array_flip(skip)
//        );
//
//        this.serviceInstantiators = XophpArray.array_merge(
//            this.serviceInstantiators,
//            newInstantiators
//        );
//
//        XophpArray<String> newManipulators = XophpArray.array_diff(
//            XophpArray.array_keys(container.serviceManipulators),
//            skip
//        );
//
//        for (String name : newManipulators) {
//            if (XophpArray.isset(this.serviceManipulators, name)) {
//                this.serviceManipulators.Set(name, XophpArray.array_merge(
//                    this.serviceManipulators.Get_by(name),
//                    container.serviceManipulators.Get_by(name)
//               ));
//            } else {
//                this.serviceManipulators.Set(name, container.serviceManipulators.Get_by(name));
//            }
//        }
    }

    /**
     * Returns true if a service is defined for $name, that is, if a call to getService($name)
     * would return a service instance.
     *
     * @param string $name
     *
     * @return bool
     */
    public boolean hasService(String name) {
        return XophpArray.isset(this.serviceInstantiators, name);
    }

    /**
     * Returns the service instance for $name only if that service has already been instantiated.
     * This is intended for situations where services get destroyed/cleaned up, so we can
     * avoid creating a service just to destroy it again.
     *
     * @note This is intended for internal use and for test fixtures.
     * Application logic should use getService() instead.
     *
     * @see getService().
     *
     * @param string $name
     *
     * @return object|null The service instance, or null if the service has not yet been instantiated.
     * @throws RuntimeException if $name does not refer to a known service.
     */
    public Object peekService(String name) {
        if (!this.hasService(name)) {
            throw new XomwNoSuchServiceException(name);
        }

        return XophpObject_.Coalesce(this.services.Get_by(name), null);
    }

    /**
     * @return string[]
     */
    public XophpArray<String> getServiceNames() {
        return XophpArray.array_keys(this.serviceInstantiators);
    }

    /**
     * Define a new service. The service must not be known already.
     *
     * @see getService().
     * @see redefineService().
     *
     * @param string $name The name of the service to register, for use with getService().
     * @param callable $instantiator Callback that returns a service instance.
     *        Will be called with this ServiceContainer instance as the only parameter.
     *        Any extra instantiation parameters provided to the constructor will be
     *        passed as subsequent parameters when invoking the instantiator.
     *
     * @throws RuntimeException if there is already a service registered as $name.
     */
    public void defineService(String name, XophpCallback instantiator) {
        // Assert::parameterType('string', $name, '$name');

        if (this.hasService(name)) {
            throw new XomwServiceAlreadyDefinedException(name);
        }

        this.serviceInstantiators.Set(name, instantiator);
    }

    /**
     * Replace an already defined service.
     *
     * @see defineService().
     *
     * @note This will fail if the service was already instantiated. If the service was previously
     * disabled, it will be re-enabled by this call. Any manipulators registered for the service
     * will remain in place.
     *
     * @param string $name The name of the service to register.
     * @param callable $instantiator Callback function that returns a service instance.
     *        Will be called with this ServiceContainer instance as the only parameter.
     *        The instantiator must return a service compatible with the originally defined service.
     *        Any extra instantiation parameters provided to the constructor will be
     *        passed as subsequent parameters when invoking the instantiator.
     *
     * @throws NoSuchServiceException if $name is not a known service.
     * @throws CannotReplaceActiveServiceException if the service was already instantiated.
     */
    public void redefineService(String name, XophpCallback instantiator) {
        // Assert::parameterType('string', $name, '$name');
        
        if (!this.hasService(name)) {
            throw new XomwNoSuchServiceException(name);
        }

        if (XophpArray.isset(this.services, name)) {
            throw new XomwCannotReplaceActiveServiceException(name);
        }

        this.serviceInstantiators.Set(name, instantiator);
        XophpArray.unset(this.disabled, name);
    }

    /**
     * Add a service manipulator callback for the given service.
     * This method may be used by extensions that need to wrap, replace, or re-configure a
     * service. It would typically be called from a MediaWikiServices hook handler.
     *
     * The manipulator callback is called just after the service is instantiated.
     * It can call methods on the service to change configuration, or wrap or otherwise
     * replace it.
     *
     * @see defineService().
     * @see redefineService().
     *
     * @note This will fail if the service was already instantiated.
     *
     * @since 1.32
     *
     * @param string $name The name of the service to manipulate.
     * @param callable $manipulator Callback function that manipulates, wraps or replaces a
     * service instance. The callback receives the new service instance and this
     * ServiceContainer as parameters, as well as any extra instantiation parameters specified
     * when constructing this ServiceContainer. If the callback returns a value, that
     * value replaces the original service instance.
     *
     * @throws NoSuchServiceException if $name is not a known service.
     * @throws CannotReplaceActiveServiceException if the service was already instantiated.
     */
    public void addServiceManipulator(String name, XophpCallback manipulator) {
        // Assert::parameterType('string', $name, '$name');

        if (!this.hasService(name)) {
            throw new XomwNoSuchServiceException(name);
        }

        if (XophpArray.isset(this.services, name)) {
            throw new XomwCannotReplaceActiveServiceException(name);
        }

        this.serviceManipulators.Xet_by_ary(name).Add(manipulator);
    }

    /**
     * Disables a service.
     *
     * @note Attempts to call getService() for a disabled service will result
     * in a DisabledServiceException. Calling peekService for a disabled service will
     * return null. Disabled services are listed by getServiceNames(). A disabled service
     * can be enabled again using redefineService().
     *
     * @note If the service was already active (that is, instantiated) when getting disabled,
     * and the service instance implements DestructibleService, destroy() is called on the
     * service instance.
     *
     * @see redefineService()
     * @see resetService()
     *
     * @param string $name The name of the service to disable.
     *
     * @throws RuntimeException if $name is not a known service.
     */
    public void disableService(String name) {
        this.resetService(name);

        this.disabled.Set(name, true);
    }

    /**
     * Resets a service by dropping the service instance.
     * If the service instances implements DestructibleService, destroy()
     * is called on the service instance.
     *
     * @warning This is generally unsafe! Other services may still retain references
     * to the stale service instance, leading to failures and inconsistencies. Subclasses
     * may use this method to reset specific services under specific instances, but
     * it should not be exposed to application logic.
     *
     * @note This is declared final so subclasses can not interfere with the expectations
     * disableService() has when calling resetService().
     *
     * @see redefineService()
     * @see disableService().
     *
     * @param string $name The name of the service to reset.
     * @param bool $destroy Whether the service instance should be destroyed if it exists.
     *        When set to false, any existing service instance will effectively be detached
     *        from the container.
     *
     * @throws RuntimeException if $name is not a known service.
     */
    protected void resetService(String name) {this.resetService(name, true);}
    protected void resetService(String name, boolean destroy) {
        // Assert::parameterType('string', $name, '$name');

        Object instance = this.peekService(name);

        if (destroy && XophpType_.instance_of(instance, XomwDestructibleService.class)) {
            ((XomwDestructibleService)instance).destroy();
        }

        XophpArray.unset(this.services, name);
        XophpArray.unset(this.disabled, name);
    }

    /**
     * Returns a service object of the kind associated with $name.
     * Services instances are instantiated lazily, on demand.
     * This method may or may not return the same service instance
     * when called multiple times with the same $name.
     *
     * @note Rather than calling this method directly, it is recommended to provide
     * getters with more meaningful names and more specific return types, using
     * a subclass or wrapper.
     *
     * @see redefineService().
     *
     * @param string $name The service name
     *
     * @throws NoSuchServiceException if $name is not a known service.
     * @throws ContainerDisabledException if this container has already been destroyed.
     * @throws ServiceDisabledException if the requested service has been disabled.
     *
     * @return object The service instance
     */
    public Object getService(String name) {
        if (this.destroyed) {
            throw new XomwContainerDisabledException();
        }

        if (XophpArray.isset(this.disabled, name)) {
            throw new XomwServiceDisabledException(name);
        }

        if (!XophpArray.isset(this.services, name)) {
            this.services.Set(name, this.createService(name));
        }

        return this.services.Get_by(name);
    }

    /**
     * @param string $name
     *
     * @throws InvalidArgumentException if $name is not a known service.
     * @return object
     */
    private Object createService(String name) {
        Object service;
        if (XophpArray.isset(this.serviceInstantiators, name)) {
            service = (this.serviceInstantiators.Get_by(name)).Call(
                this,
                this.extraInstantiationParams
			);

            if (XophpArray.isset(this.serviceManipulators, name)) {
                Object ret;
                for (XophpCallback callback : this.serviceManipulators.Get_by(name)) {
                    ret = XophpCallback.call_user_func_array(
                        callback,
                        XophpArray.array_merge(XophpArray.New(service, this), this.extraInstantiationParams)
					);

                    // If the manipulator callback returns an object, that object replaces
                    // the original service instance. This allows the manipulator to wrap
                    // or fully replace the service.
                    if (ret != null) {
                        service = ret;
                    }
                }
            }

            // NOTE: when adding more wiring logic here, make sure importWiring() is kept in sync!
        } else {
            throw new XomwNoSuchServiceException(name);
        }

        return service;
    }

    /**
     * @param string $name
     * @return bool Whether the service is disabled
     * @since 1.28
     */
    public boolean isServiceDisabled(String name) {
        return XophpArray.isset(this.disabled, name);
    }
}
