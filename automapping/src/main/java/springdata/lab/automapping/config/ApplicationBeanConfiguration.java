package springdata.lab.automapping.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springdata.lab.automapping.data.entities.Employee;
import springdata.lab.automapping.services.dtos.EmployeeViewDto;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, String> stringConverter = new Converter<String, String>() {
            @Override
            public String convert(MappingContext<String, String> mappingContext) {
                return mappingContext.getSource() == null ? null : mappingContext.getSource().toLowerCase();
            }
        };

        /* Lambda version of the same thing - newer aproach!
        TypeMap<Employee, EmployeeViewDto> typeMap =
                modelMapper.createTypeMap(Employee.class, EmployeeViewDto.class);

        typeMap.addMapping(
                src ->
                        src.getAddress().getCity().getName(),
                        EmployeeViewDto::setAddressCityName
        );

        */
        /* Can manually set mapping if names in entities doesn't match exactly
                        (Better match names EXACTLY!!)
        */

        PropertyMap<Employee, EmployeeViewDto> propertyMap = new PropertyMap<Employee, EmployeeViewDto>() {
            @Override
            protected void configure() {
                map().setAddressCityName(source.getAddress().getCity().getName());
                using(stringConverter).map(source.getFirstName()).setFirstName(null);
            }
        };

        //modelMapper.addConverter(stringConverter);
        modelMapper.addMappings(propertyMap);



        return modelMapper;
    }

}
