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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_section_list__merge__tst {
	private final    Xop_section_list__fxt fxt = new Xop_section_list__fxt();
	@Test   public void Basic() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		);
		fxt.Test__merge_bry_or_null("Hdr_2", String_.Concat_lines_nl_skip_last
		( "== Hdr 2 =="
		, "Para 2a"
		), String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2a"
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		)
		);
	}
	@Test   public void Nl_many() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, ""
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		, ""
		, ""
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		);
		fxt.Test__merge_bry_or_null("Hdr_2", String_.Concat_lines_nl_skip_last
		( "== Hdr 2 =="
		, "Para 2a"
		), String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, ""
		, ""
		, "== Hdr 2 =="
		, "Para 2a"
		, ""
		, ""
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		)
		);
	}
	@Test   public void Bos() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__merge_bry_or_null("Hdr_1", String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1a"
		), String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1a"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		)
		);
	}
	@Test   public void Bos__ws() {
		fxt.Exec__parse
		( ""
		, "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__merge_bry_or_null("Hdr_1", String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1a"
		), String_.Concat_lines_nl_skip_last
		( ""
		, "== Hdr 1 =="
		, "Para 1a"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		)
		);
	}
	@Test   public void Eos() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__merge_bry_or_null("Hdr_2", String_.Concat_lines_nl_skip_last
		( "== Hdr 2 =="
		, "Para 2a"
		), String_.Concat_lines_nl_skip_last
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2a"
		)
		);
	}
	@Test   public void Lead() {
		fxt.Exec__parse
		( "lead para"
		, ""
		, "== Hdr 1 =="
		, "Para 1"
		);
		fxt.Test__merge_bry_or_null("", String_.Concat_lines_nl_skip_last
		( "lead para 1"
		, ""
		, "lead para 2"
		), String_.Concat_lines_nl_skip_last
		( "lead para 1"
		, ""
		, "lead para 2"
		, "== Hdr 1 =="
		, "Para 1"
		)
		);
	}
	@Test   public void Lead__new() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		);
		fxt.Test__merge_bry_or_null("", String_.Concat_lines_nl_skip_last
		( "lead para 1"
		, ""
		), String_.Concat_lines_nl_skip_last
		( "lead para 1"
		, "== Hdr 1 =="
		, "Para 1"
		)
		);
	}
}
