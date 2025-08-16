package dev.tilera.cwg.modules;

import cpw.mods.fml.common.discovery.ASMDataTable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleDiscoverer {

    private ASMDataTable asmDataTable;

    public ModuleDiscoverer(ASMDataTable asmDataTable) {
        this.asmDataTable = asmDataTable;
    }

    public Stream<Class<? extends IModule>> discover() {
        return asmDataTable.getAll(Module.class.getCanonicalName())
                .stream()
                .map(ASMDataTable.ASMData::getClassName)
                .map(name -> {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(IModule.class::isAssignableFrom)
                .map(clazz -> (Class<? extends IModule>) clazz);
    }

    public boolean hasEmptyConstructor(Class<?> clazz) {
        return Stream.of(clazz.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    public Stream<? extends IModule> instantiate(Stream<Class<? extends IModule>> classes) {
        return classes
                .filter(this::hasEmptyConstructor)
                .map(clazz -> {
                    try {
                        return clazz.newInstance();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    public Set<IModule> process() {
        return instantiate(discover()).collect(Collectors.toSet());
    }

}
