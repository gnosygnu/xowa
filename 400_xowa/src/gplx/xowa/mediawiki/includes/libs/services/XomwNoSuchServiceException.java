package gplx.xowa.mediawiki.includes.libs.services;

import gplx.String_;
import gplx.xowa.mediawiki.XophpException;
import gplx.xowa.mediawiki.XophpRuntimeException;

/**
 * Exception thrown when the requested service is not known.
 */
class XomwNoSuchServiceException extends XophpRuntimeException {

    /**
     * @param string $serviceName
     * @param Exception|null $previous
     */
    public XomwNoSuchServiceException(String serviceName) {this(serviceName, null);}
    public XomwNoSuchServiceException(String serviceName, XophpException previous) {
        super(String_.Format("No such service: {0}", serviceName), 0, previous);
    }

}
