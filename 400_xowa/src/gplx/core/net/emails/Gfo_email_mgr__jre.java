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
package gplx.core.net.emails; import gplx.*; import gplx.core.*; import gplx.core.net.*;
import gplx.langs.htmls.encoders.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
class Gfo_email_mgr__jre implements Gfo_email_mgr {
	public void Send(String to, String subject, String body) {
		try {
			Gfo_url_encoder url_encoder = gplx.langs.htmls.encoders.Gfo_url_encoder_.Fsys_wnt;
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
