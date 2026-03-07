package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.PersonalAdministrativoDAO;
import com.seguridadnacional.seguridad.models.PersonalAdministrativo;
import java.sql.SQLException;
import java.util.List;
public class PersonalAdministrativoController {
    private final PersonalAdministrativoDAO dao = new PersonalAdministrativoDAO();
    public void crear(PersonalAdministrativo pa) { try { dao.insertar(pa); } catch (SQLException e) { throw new RuntimeException("Error al crear personal: " + e.getMessage(), e); } }
    public PersonalAdministrativo buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<PersonalAdministrativo> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(PersonalAdministrativo pa) { try { dao.actualizar(pa); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}