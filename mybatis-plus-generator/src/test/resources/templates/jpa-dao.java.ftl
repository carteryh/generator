package ${package.Mapper};

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description:${table.comment!}
 * </p>
 * @author ${author}
 * @date ${date}
 */
public interface ${table.mapperName} extends ${superMapperClass}<${entity},Long> {


}
