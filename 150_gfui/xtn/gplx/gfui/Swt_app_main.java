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
package gplx.gfui;
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
public class Swt_app_main {
	public static void main(String[] args) {
//		Drag_drop();
//		List_fonts();
//		keystrokes(args);
		Permission_denied();
	}
	static void Drag_drop() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		for (int i = 0; i < 10; i++) {
			CTabItem item = new CTabItem(folder, SWT.NONE);
			item.setText("item "+i);
			Text text = new Text(folder, SWT.BORDER | SWT.MULTI | SWT.VERTICAL);
			text.setText("Text control for "+i);
			item.setControl(text);
			if (i == 9) {
				item.setShowClose(false);
				item.setText("+");
//				item.setImage(new Image(Display.getDefault(), "J:\\gplx\\xowa\\user\\anonymous\\app\\img\\edit\\format-bold-A.png"));
			}
		}
		ToolBar t = new ToolBar( folder, SWT.FLAT );
		ToolItem i = new ToolItem( t, SWT.PUSH );
		i.setText( "add" );
		folder.setTopRight( t, SWT.RIGHT );
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
    static void keystrokes(String[] args) 
    {

        Display display = new Display ();

        final Shell shell = new Shell (display);

//        display.addFilter(SWT.KeyDown, new Listener() {
//
//            public void handleEvent(Event e) {
//                if(((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f'))
//                {
//                    System.out.println("From Display I am the Key down !!" + e.keyCode);
//                }
//            }
//        });
        shell.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
//                if(((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f'))
//                {
//                    shell.setBackground(orig);
//                    System.out.println("Key up !!");
//                }
            	System.out.println(e.stateMask + " " + e.keyCode);
            }
            public void keyPressed(KeyEvent e) {
//            	System.out.println(e.stateMask + " " + e.keyCode);
            }
        });
        shell.addMouseListener(new MouseListener() {			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.button);
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

        shell.setSize (200, 200);
        shell.open ();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();

    }
	static void List_fonts() {
		java.awt.GraphicsEnvironment e = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
		java.awt.Font[] fonts = e.getAllFonts(); // Get the fonts
		for (java.awt.Font f : fonts) {
		  System.out.println(f.getFontName());
		}		
	}
	static void Permission_denied() {
		String html
			= "<html>\n"
			+ "<head>\n" 
			+ "<script>\n"
			+ "  function permissionDeniedExample() {\n"
			+ "    var sel = window.getSelection();\n" 
			+ "    alert('calling sel.rangeCount');\n"
			+ "    alert('sel.rangeCount = ' + sel.rangeCount);\n"
			+ "  }\n"
			+ "</script>\n"
			+ "</head>\n"
			+ "<body>\n" 
			+ "  <a href='#direct_call_fails'/>click to call permissionDeniedExample -> will throw error and not show sel.rangeCount</a><br/>\n"
			+ "  <a href='#wrapped_call_works'/>click to call permissionDeniedExample inside a setTimeout -> will show sel.rangeCount</a><br/>\n"
			+ "</body>\n"
			+ "</html>\n"
			;

		System.setProperty
		( "org.eclipse.swt.browser.XULRunnerPath"
		// ADJUST THIS PATH AS NECESSARY ON YOUR MACHINE
		, "C:\\xowa\\bin\\windows\\xulrunner"
		);
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.MOZILLA);	// changed from none
			browser.addLocationListener(new LocationListener() {				
				@Override
				public void changing(LocationEvent arg0) {
					if (arg0.location.equals("about:blank")) return;
					arg0.doit = false;
				}
				
				@Override
				public void changed(LocationEvent arg0) {
					String location = arg0.location;
					if (location.equals("about:blank")) return;
					
					// build code
					String code = "alert('unknown_link:" + location + "')";
					if 		(location.contains("direct_call_fails"))
						code = "permissionDeniedExample();";
					else if (location.contains("wrapped_call_works"))
						code = "setTimeout(function(){permissionDeniedExample();}, 1);";
					
					// evaluate code
					try {
						browser.evaluate(code);
					} catch (Exception e) {
						System.out.println(e);
					}
					arg0.doit = false;
				}
			});
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		browser.setText(html);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
