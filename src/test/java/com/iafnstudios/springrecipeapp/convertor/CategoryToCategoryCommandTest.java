package com.iafnstudios.springrecipeapp.convertor;

import com.iafnstudios.springrecipeapp.command.CategoryCommand;
import com.iafnstudios.springrecipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        //then
        assertEquals(ID_VALUE,categoryCommand.getId());
        assertEquals(DESCRIPTION,categoryCommand.getDescription());
    }
}