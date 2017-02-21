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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import java.io.*;
import java.util.zip.*;
import gplx.core.envs.*; import gplx.core.threads.*; import gplx.core.progs.*;
public class Io_zip_compress_cmd__jre {
	public Io_zip_compress_cmd__jre() {}
	public Io_zip_compress_cmd__jre Make_new() {return new Io_zip_compress_cmd__jre();}
	public byte Exec_hook(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url[] src_urls, Io_url trg_url, String resume_name, long resume_file, long resume_item) {
				OutputStream trg_out_stream;
		Io_mgr.Instance.CreateDirIfAbsent(trg_url.OwnerDir());
		try {trg_out_stream = new FileOutputStream(trg_url.Xto_api());}
		catch (Exception e) {throw Err_.new_exc(e, "io", "trg open failed", "url", trg_url.Raw());}
		ZipOutputStream trg_stream = new ZipOutputStream(trg_out_stream);
		int len = src_urls.length;
		byte[] buffer = new byte[4096];
		for (int i = 0; i < len; ++i) {
			Io_url src_url = src_urls[i];
			java.util.zip.ZipEntry trg_entry = new java.util.zip.ZipEntry(src_url.NameAndExt());
			try {trg_stream.putNextEntry(trg_entry);}
			catch (Exception e) {
				try {trg_stream.close();}
				catch (IOException e1) {}
				throw Err_.new_exc(e, "io", "zip entry failed", "url", src_url.Raw());
			}
			FileInputStream src_stream = null;
			try {src_stream = new FileInputStream(new File(src_url.Raw()));}
			catch (Exception e) {throw Err_.new_exc(e, "io", "src open failed", "url", src_url.Raw());}
			while (true) {	// loop over bytes
				int read_in_raw = -1;
				try {read_in_raw = src_stream.read(buffer);}
				catch (Exception e) {
					try {src_stream.close();}
					catch (IOException e1) {}
					throw Err_.new_exc(e, "io", "src read failed", "url", src_url.Raw());
				}
				if (read_in_raw < 1) break;
				try {trg_stream.write(buffer, 0, read_in_raw);}
				catch (Exception e) {
					try {src_stream.close();}
					catch (IOException e1) {}
					throw Err_.new_exc(e, "io", "trg write failed", "url", trg_url.Raw());
				}
			}
			try {
				trg_stream.closeEntry();				
				src_stream.close();
			}
			catch (Exception e) {throw Err_.new_exc(e, "io", "trg close entry failed", "url", src_url.Raw());}
		}
		try {
			trg_stream.close();
			trg_stream.flush();			
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "trg close failed", "url", trg_url.Raw());}
		return Gfo_prog_ui_.Status__done;
			}
}
