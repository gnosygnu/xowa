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
package gplx.core.intls.ucas; import gplx.*; import gplx.core.*; import gplx.core.intls.*;
import java.util.Locale;
import com.ibm.icu.text.CollationKey;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.RuleBasedCollator;
class Uca_collator__icu__4_8 implements Uca_collator {
	private Collator collator;
	public void	Init(String locale, boolean numeric_ordering) {
		try {
			this.collator = Collator.getInstance(Locale.forLanguageTag(locale));
			if (numeric_ordering) {
				RuleBasedCollator rbc = (RuleBasedCollator)collator;
				rbc.setNumericCollation(true);
			}
		} catch (Exception e) {throw Err_.new_wo_type("collator init failed", "err", Err_.Message_lang(e));}		
	}
	public byte[]	Get_sortkey(String s) {
		CollationKey key = collator.getCollationKey(s);		
		byte[] src = key.toByteArray();
		int src_len = src.length;
		byte[] rv = src;
		
		// remove last byte if it is 0 (which it often is) 
		if (src_len > 0 && src[src_len - 1] == 0) {
			int rv_len = src_len - 1;
			rv = new byte[rv_len];
			for (int i = 0; i < rv_len; ++i)
				rv[i] = src[i];
		} 
		return rv;
	}
}
