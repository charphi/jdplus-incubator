/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.desktop.highfreq.ui.actions;

import demetra.desktop.highfreq.ExtendedAirlineDocumentManager;
import demetra.desktop.workspace.DocumentUIServices;
import demetra.desktop.workspace.WorkspaceItem;
import demetra.desktop.workspace.nodes.WsNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jdplus.highfreq.extendedairline.ExtendedAirlineDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

@ActionID(category = "Tools",
id = "demetra.desktop.highfreq.ui.OpenExtendedAirlineDoc")
@ActionRegistration(displayName = "#CTL_OpenExtendedAirlineDoc")
@ActionReferences({
    @ActionReference(path = ExtendedAirlineDocumentManager.ITEMPATH, position = 1600, separatorBefore = 1590)
})
@NbBundle.Messages("CTL_OpenExtendedAirlineDoc=Open")
public class OpenExtendedAirlineDoc implements ActionListener {

    private final WsNode context;

    public OpenExtendedAirlineDoc(WsNode context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        WorkspaceItem<ExtendedAirlineDocument> doc = context.getWorkspace().searchDocument(context.lookup(), ExtendedAirlineDocument.class);
        DocumentUIServices ui = DocumentUIServices.forDocument(ExtendedAirlineDocument.class);
        if (ui != null) {
            ui.showDocument(doc);
        }
    }
}
