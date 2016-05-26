package org.eclipse.papyrus.moka.fmi.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ExportFMUDialog extends Dialog {

	protected String selectedDir = "" ;
	protected String selectedPlatform = "" ;
	protected boolean embedJre = false ;
	protected String selectedJrePath = "" ;
	
	protected Button selectTargetDirButton ;
	protected Text selectedDirText ;
	protected Combo combo ;
	protected Button isJreEmbedded ;
	protected Button selectJREButton ;
	protected Text selectedJrePathText ;
	
	public ExportFMUDialog(Shell parentShell) {
		super(parentShell);
	}

	
	
	@Override
	protected Control createButtonBar(Composite parent) {
		Control control = super.createButtonBar(parent);
		this.getButton(IDialogConstants.OK_ID).setEnabled(false);
		return control ;
	}



	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE) ;
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    GridLayout layout = new GridLayout(2, false);
	    container.setLayout(layout);
		
	    createSelectDir(container);
	    createSelectPlatform(container);
	    createEmbbedJRE(container) ;
		createSelectJRE(container);
		
		selectTargetDirButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = container.getShell();
				DirectoryDialog dirDialog = new DirectoryDialog(shell);
				dirDialog.setText("Select target directory");
				selectedDir = dirDialog.open();
				if (selectedDir != null) {
					selectedDirText.setText(selectedDir);
				}
				else {
					selectedDir = "" ;
				}
				checkInformation();
			}
		});
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedPlatform = combo.getText() ;
				checkInformation();
			}
		});
		
		isJreEmbedded.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				embedJre = !embedJre ;
				if (embedJre == true) {
					selectJREButton.setEnabled(true);
					selectedJrePathText.setEnabled(true);
				}
				else {
					selectJREButton.setEnabled(false);
					selectedJrePathText.setEnabled(false);
				}
				checkInformation();
			}
		});
		
		selectJREButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = container.getShell();
				DirectoryDialog dirDialog = new DirectoryDialog(shell);
				dirDialog.setText("Select JRE path");
				selectedJrePath = dirDialog.open();
				if (selectedJrePath != null) {
					selectedJrePathText.setText(selectedJrePath);
				}
				else {
					selectedJrePath = "" ;
				}
				checkInformation();
			}
		});
		
		return container;
	}

	private void createSelectDir(Composite container) {
		selectTargetDirButton = new Button(container, SWT.PUSH);
		selectTargetDirButton.setText("Select target directory");
		
		GridData dataDirText = new GridData();
	    dataDirText.grabExcessHorizontalSpace = true;
	    dataDirText.horizontalAlignment = GridData.FILL;
		
		selectedDirText = new Text(container, SWT.BORDER | SWT.PUSH);
		selectedDirText.setText(selectedDir);
		selectedDirText.setLayoutData(dataDirText);
	}
	
	private void createSelectPlatform(Composite container) {
		Label label = new Label(container, SWT.NONE) ;
		label.setText("Target platform:");
		
		combo = new Combo(container, SWT.DROP_DOWN | SWT.PUSH);
		combo.setItems("Linux", "Windows");
		combo.setText("Linux");
		selectedPlatform = "Linux" ;
	}
	
	private void createEmbbedJRE(Composite container) {
		Label label = new Label(container, SWT.NONE) ;
		label.setText("Include JRE:");
		
		isJreEmbedded = new Button(container, SWT.CHECK) ;
		isJreEmbedded.setText("");
	}
	
	private void createSelectJRE(Composite container) {
		selectJREButton = new Button(container, SWT.PUSH);
		selectJREButton.setText("Select JRE path");
		selectJREButton.setEnabled(false);
		
		GridData dataJREText = new GridData();
	    dataJREText.grabExcessHorizontalSpace = true;
	    dataJREText.horizontalAlignment = GridData.FILL;
		
	    selectedJrePathText = new Text(container, SWT.BORDER | SWT.PUSH);
		selectedJrePathText.setText(selectedJrePath);
		selectedJrePathText.setLayoutData(dataJREText);
	}
	

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Export FMU");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(550, 300);
	}

	protected void checkInformation() {
		boolean validSelectedDir = ! ("".equals(selectedDir)) ;
		boolean validSelectedPlatform = "Linux".equals(selectedPlatform) || "Windows".equals(selectedPlatform) ;
		boolean validJre = embedJre ? !("".equals(selectedJrePath)) : true ;
		if (validSelectedDir && validSelectedPlatform && validJre) {
			this.getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		else {
			this.getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}
	
	public String getSelectedDir() {
		return selectedDir;
	}

	public String getSelectedPlatform() {
		return selectedPlatform;
	}

	public boolean isEmbedJre() {
		return embedJre;
	}

	public String getSelectedJrePath() {
		return selectedJrePath;
	}
	
}
