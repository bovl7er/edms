package com.cmxv.tree.processors.impls;

import javax.faces.context.FacesContext;

import com.cmxv.tree.processors.interfaces.ProcessableNode;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import com.cmxv.weblayer.managedbeans.MainPageController;

/**
 * Меняет правую часть экрана на указанный шаблон.
 *
 * @author VLAD
 *
 */
public class SetRightTemplateNode extends DefaultTreeNode implements ProcessableNode {

    private static final long serialVersionUID = -720667805353286354L;
    private String template;
    private String title;
//--------------------------------------------------------------------------------------------------------------------

    public SetRightTemplateNode(String title, String template) {
        super(title);
        this.template = template;
        this.title = title;
    }
//--------------------------------------------------------------------------------------------------------------------

    public SetRightTemplateNode(String title, String template, TreeNode parent) {
        this(title, template);
        this.setParent(parent);
        parent.getChildren().add(this);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Процесс,выполняющийся при выборе узла дерева
     */
    @Override
    public void process() {
        FacesContext context = FacesContext.getCurrentInstance();
        MainPageController mainPageComtroller = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
        mainPageComtroller.setRightTemplate(template);
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public String getTitle() {
        return this.title;
    }
//--------------------------------------------------------------------------------------------------------------------
}
