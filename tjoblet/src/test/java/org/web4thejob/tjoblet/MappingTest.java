package org.web4thejob.tjoblet;

import org.junit.Test;
import org.web4thejob.context.ContextUtil;
import org.web4thejob.tjoblet.base.AbstractORMTest;
import org.web4thejob.tjoblet.orm.Category;
import org.web4thejob.tjoblet.orm.CategoryHierarchy;

/**
 * @author Veniamin Isaias
 * @since 1.0.0
 */
public class MappingTest extends AbstractORMTest {

    @Test
    public void test1() {
        Category category1 = new Category();
        category1.setName("C1");
        ContextUtil.getDWS().save(category1);

        Category category2 = new Category();
        category2.setName("C2");
        ContextUtil.getDWS().save(category2);

        Category category3 = new Category();
        category3.setName("C3");
        ContextUtil.getDWS().save(category3);

        CategoryHierarchy hierarchy = new CategoryHierarchy();
        hierarchy.setParent(category1);
        hierarchy.setChild(category2);
        hierarchy.setSorting(1);
        ContextUtil.getDWS().save(hierarchy);

        hierarchy.setAsNew();
        hierarchy.setParent(category1);
        hierarchy.setChild(category3);
        hierarchy.setSorting(2);
        ContextUtil.getDWS().save(hierarchy);


        System.out.println();
    }

}
