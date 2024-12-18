package br.com.erudio.Converter;

import java.util.ArrayList;
import java.util.List;

import br.com.erudio.Data.Model.Person;
import br.com.erudio.Data.VO.V1.PersonVO;
import org.modelmapper.ModelMapper;

public class DozerConverter {

    private static ModelMapper mapper = new ModelMapper();

    static {
        mapper.createTypeMap(Person.class, PersonVO.class).addMapping(Person::getId, PersonVO::setKey);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
