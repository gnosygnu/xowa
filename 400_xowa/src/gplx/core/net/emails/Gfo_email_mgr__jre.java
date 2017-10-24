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
package gplx.core.net.emails; import gplx.*; import gplx.core.*; import gplx.core.net.*;
import gplx.langs.htmls.encoders.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
class Gfo_email_mgr__jre implements Gfo_email_mgr {
	public void Send(String to, String subject, String body) {
		try {
			Gfo_url_encoder url_encoder = Gfo_url_encoder_.New__fsys_wnt().Make();
			subject = url_encoder.Encode_str(subject);
			body = url_encoder.Encode_str(body);
			body = String_.Replace(body, "`", "%60");
			String url_str = "mailto:gnosygnu+xowa_xolog@gmail.com?subject=" + subject + "&body=" + body;
			URI uri = new URI(url_str);
			Desktop.getDesktop().mail(uri);
		} catch (Exception e) {
			Gfo_log_.Instance.Warn("email failed", "subject", subject, "body", body, "err", Err_.Message_gplx_log(e));
		}
	}
}
