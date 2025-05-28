package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Reabastecimiento;

public interface ReabastecimientoService {
    Reabastecimiento guardar(Reabastecimiento r);
    List<Reabastecimiento> listarTodos();
}
