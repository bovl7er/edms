package com.cmxv.weblayer.managedbeans;

import com.cmxv.tree.processors.impls.AuditNode;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cmxv.tree.processors.impls.SetRightTemplateNode;
import com.cmxv.tree.processors.interfaces.ProcessableNode;
import com.cmxv.tree.processors.impls.DocumentsNode;

@Component
@Scope(value = "session")
public class LeftTreeController implements Serializable {

    private static final long serialVersionUID = 5338960615601898245L;

    private TreeNode selectedNode;

    private String selectedNodeTitle;

    private TreeNode root;
    
    @Autowired
    private AuthController authController;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Выбор узла дерева
     * @param event события выбора узла
     */
    public void onNodeSelect(NodeSelectEvent event) {

        if (selectedNode instanceof ProcessableNode) {
            ((ProcessableNode) selectedNode).process();
        }
    }

//--------------------------------------------------------------------------------------------------------------------
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

//--------------------------------------------------------------------------------------------------------------------
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

//--------------------------------------------------------------------------------------------------------------------
    public String getSelectedNodeTitle() {
        return selectedNodeTitle;
    }

//--------------------------------------------------------------------------------------------------------------------
    public void setSelectedNodeTitle(String selectedNodeTitle) {
        this.selectedNodeTitle = selectedNodeTitle;
    }

//--------------------------------------------------------------------------------------------------------------------
    @PostConstruct
    public void init() {


    }

//--------------------------------------------------------------------------------------------------------------------
    public TreeNode getRoot() {
        root = new DefaultTreeNode("Root", null);

        
        if(authController.checkRights("Документы")) {
            TreeNode docs = new DocumentsNode("Документы", "documents.xhtml", "Документы", root);
            docs.setExpanded(true);

            docs.getChildren().add(new DocumentsNode("Заявления", "documents.xhtml", "Заявления"));
            if(authController.checkRights("Архив")) {
                docs.getChildren().add(new DocumentsNode("Архив", "documents.xhtml", "Архив"));            	
            }
        }


        if(authController.checkRights("Администрирование")) {
            TreeNode admin = new SetRightTemplateNode("Администрирование", "admin.xhtml", root);
            admin.setExpanded(true);
        	admin.getChildren().add(new SetRightTemplateNode("Пользователи", "admin/users.xhtml"));
            admin.getChildren().add(new SetRightTemplateNode("Роли", "admin/roles.xhtml"));
        }
       
        if(authController.checkRights("Аудит")) {
        	 TreeNode audit = new DefaultTreeNode("Аудит", root);
             audit.setExpanded(true);
             audit.setSelectable(false);

             audit.getChildren().add(new AuditNode("Аудит документов", "audit/auditDocument.xhtml", "Документы"));
             audit.getChildren().add(new AuditNode("Аудит пользователей", "audit/auditUser.xhtml", "Пользователи"));
        }
       
    	return root;
    }

//--------------------------------------------------------------------------------------------------------------------
}
