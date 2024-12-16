package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    @Autowired
    private Sql2o sql2o;

    // Encontrar todas las categorias.
    @Override
    public List<CategoryEntity> findAllCategories(){
        String query = "SELECT * FROM category";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .executeAndFetch(CategoryEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Encontrar categoría por ID
    @Override
    public CategoryEntity findCategoryById(Long category_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM category WHERE category_id = :category_id")
                    .addParameter("category_id", category_id)
                    .executeAndFetchFirst(CategoryEntity.class); // Cambiado a `executeAndFetchFirst`
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve null en caso de error (manejo básico de excepciones)
        }
    }

    // Guardar una nueva categoría
    @Override
    public void saveCategory(CategoryEntity category) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO category (category_id, category_name) VALUES (:category_id, :category_name)")
                    .addParameter("category_id", category.getCategory_id())
                    .addParameter("category_name", category.getCategory_name())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar una categoría por ID
    @Override
    public void updateCategory(CategoryEntity category) {
        String query =
                """
                UPDATE category
                SET
                category_name = :category_name
                WHERE
                category_id = :category_id
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery(query)
                    .addParameter("category_id", category.getCategory_id())
                    .addParameter("category_name", category.getCategory_name())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar categoría por ID
    @Override
    public void deleteCategoryById(Long category_id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM category WHERE category_id = :category_id")
                    .addParameter("category_id", category_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Encontrar categoría por nombre
    @Override
    public CategoryEntity findCategoryByName(String category_name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM category WHERE category_name = :category_name")
                    .addParameter("category_name", category_name)
                    .executeAndFetchFirst(CategoryEntity.class); // `executeAndFetchFirst` asegura un solo resultado
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo de errores
        }
    }
}
