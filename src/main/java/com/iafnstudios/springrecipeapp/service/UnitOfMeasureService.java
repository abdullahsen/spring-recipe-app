package com.iafnstudios.springrecipeapp.service;

import com.iafnstudios.springrecipeapp.command.UnitOfMeasureCommand;


import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
