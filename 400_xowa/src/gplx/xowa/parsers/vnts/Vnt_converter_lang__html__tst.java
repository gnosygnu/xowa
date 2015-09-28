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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
public class Vnt_converter_lang__html__tst {	// REF: https://www.mediawiki.org/wiki/Writing_systems/Syntax
	private final Vnt_converter_lang_fxt fxt = new Vnt_converter_lang_fxt();
	private String rule;
	@Before public void init() {
		rule = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-";
	}
	@Test   public void Node() {
		fxt.Test_parse(rule + "hk<span>hk</span>hk", "cn<span>cn</span>cn");
	}
	@Test   public void Attribs() {
		fxt.Test_parse(rule + "<span class='hk'>hk</span>", "<span class='hk'>cn</span>");
	}
	@Test   public void Attribs__title() {
		fxt.Test_parse(rule + "<span title='hk'>hk</span>", "<span title='cn'>cn</span>");
	}
	@Test   public void Attribs__alt() {
		fxt.Test_parse(rule + "<span alt='hk'>hk</span>", "<span alt='cn'>cn</span>");
	}
	@Test   public void Attribs__skip_url() {
		fxt.Test_parse(rule + "<span alt='http://hk.org'>hk</span>", "<span alt='http://hk.org'>cn</span>");
	}
	@Test   public void Node__style() {
		fxt.Test_parse(rule + "hk<script>hk</script>hk", "cn<script>hk</script>cn");
	}
	@Test   public void Node__code() {
		fxt.Test_parse(rule + "hk<code>hk</code>hk", "cn<code>hk</code>cn");
	}
	@Test   public void Node__pre() {
		fxt.Test_parse(rule + "hk<pre>hk</pre>hk", "cn<pre>hk</pre>cn");
	}
	@Test   public void Node__pre__nested() {
		fxt.Test_parse(rule + "hk<pre><span>hk</span></pre>hk", "cn<pre><span>hk</span></pre>cn");
	}
}
