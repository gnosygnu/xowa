package gplx.xowa.mediawiki.includes.libs.services;

import gplx.Err_;
import gplx.core.tests.Gftest;
import gplx.tests.Gftest_fxt;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpCallback;
import gplx.xowa.mediawiki.XophpCallbackOwner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XomwServiceContainerTest {
    private XomwServiceContainer container = new XomwServiceContainer();
    private XomwServiceContainerExample wiring = new XomwServiceContainerExample();
    private XomwServiceManipulatorExample manipulator = new XomwServiceManipulatorExample();

    @Test
    public void basic() {
        XophpArray<String> extraInstantiatorArg = XophpArray.<String>New("a", "b");
        XomwServiceContainer container = new XomwServiceContainer(extraInstantiatorArg);

        // applyWiring -> adds servicesInstantator by calling defineService
        container.applyWiring(wiring.NewWirings("test1", "test2"));

        // hasService -> checks if instantiator exists
        Gftest.Eq__bool_y(container.hasService("test1"));
        Gftest.Eq__bool_n(container.hasService("test9"));

        // getServiceNames -> gets list of instantiators
        Gftest.Eq__str(XophpArray.<String>New("test1", "test2").To_str(), container.getServiceNames().To_str());

        // getService -> creates service
        XomwServiceExample service = (XomwServiceExample)container.getService("test1");
        Gftest.Eq__str("test1", service.Name());
        Gftest.Eq__int(0, service.InstanceNumber());
        Gftest.Eq__obj_or_null(extraInstantiatorArg, service.ExtraInstantiatorArg());

        // getService again -> retrieves service
        service = (XomwServiceExample)container.getService("test1");
        Gftest.Eq__int(0, service.InstanceNumber());

        // resetService + getService -> recreates service
        container.resetService("test1");
        service = (XomwServiceExample)container.getService("test1");
        Gftest.Eq__int(1, service.InstanceNumber());

        // peekService -> returns service if created
        service = (XomwServiceExample)container.peekService("test1");
        Gftest.Eq__int(1, service.InstanceNumber());

        // peekService -> returns null if not created
        Gftest.Eq__obj_or_null(null, (XomwServiceExample)container.peekService("test2"));

        // disableService -> disables service
        container.disableService("test1");

        // isServiceDisabled -> checks disabled state
        Gftest.Eq__bool_y(container.isServiceDisabled("test1"));
        Gftest.Eq__bool_n(container.isServiceDisabled("test2"));

        // resetService -> also reenables service
        container.resetService("test1");
        Gftest.Eq__bool_n(container.isServiceDisabled("test1"));

        // redefineService -> redefines instantiator if not called
        container.redefineService("test2", wiring.NewCallback("test2a"));
        service = (XomwServiceExample)container.getService("test2");
        Gftest.Eq__str("test2a", service.Name());

        // addServiceManipulator -> adds extra callback
        container.resetService("test2");
        container.addServiceManipulator("test2", manipulator.NewCallback("manip1"));
        service = (XomwServiceExample)container.getService("test2");
        Gftest.Eq__str("manip1", service.Name());

        // destroy ->

        // loadWiringFiles ->

        // importWiring ->
    }

    static class XomwServiceExample {
        private static int instanceNumberCounter = 0;
        public XomwServiceExample(String name, Object extraInstantiatorArg) {
            this.name = name;
            this.extraInstantiatorArg = extraInstantiatorArg;
            this.instanceNumber = instanceNumberCounter++;
        }
        public String Name() {return name;} private final String name;
        public Object ExtraInstantiatorArg() {return extraInstantiatorArg;} private final Object extraInstantiatorArg;
        public int InstanceNumber() {return instanceNumber;} private final int instanceNumber;
    }
    class XomwServiceContainerExample implements XophpCallbackOwner {
        private XophpArray<XophpCallback> wirings;
        @Override
        public Object Call(String method, Object... args) {
            XomwServiceContainer mediaWikiServices = (XomwServiceContainer)args[0];
            return new XomwServiceExample(method, args[1]);
        }
        public XophpArray<XophpCallback> NewWirings(String... names) {
            wirings = new XophpArray();
            for (String name : names) {
                wirings.Add(name, this.NewCallback(name));
            }
            return wirings;
        }
    }
    class XomwServiceManipulatorExample implements XophpCallbackOwner {
        @Override
        public Object Call(String method, Object... args) {
            XophpArray manipulatorArgs = (XophpArray)args[0];
            return new XomwServiceExample(method, manipulatorArgs);
        }
    }
}