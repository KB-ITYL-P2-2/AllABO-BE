package com.allabo.fyl.fyl_server.config;//package com.allabo.fyl.fyl_server.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.allabo.fyl.fyl_server.service")
public class AppConfig {

}
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
////        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        config.setJdbcUrl("jdbc:mysql://localhost:3306/shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8&useUnicode=true");
//
//        config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//        config.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8&useUnicode=true");
//        config.setUsername("root");
//        config.setPassword("1234");
//
//        config.setConnectionTimeout(30000); //풀에서 연결을 가져오기 위해 대기할 최대 시간(밀리초). 기본값은 30,000ms (30초)
//        config.setMinimumIdle(1); //풀의 최소 연결 크기입니다. 기본값은 5입니다
//        config.setMaximumPoolSize(5);//풀의 최대 크기입니다. 기본값은 10입니다.
//        config.setIdleTimeout(600000); //연결이 유휴 상태로 유지될 최대 시간(밀리초)입니다. 기본값은 600,000ms (10분)입니다.
//        config.setMaxLifetime(1800000);//풀의 연결이 최대 유지될 시간(밀리초)입니다. 기본값은 1,800,000ms (30분)입니다.
//        config.setAutoCommit(true);//커넥션의 자동 커밋 여부를 설정합니다. 기본값은 true입니다.
//
//        return new HikariDataSource(config);
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
////1. 마이바티스 설정 : mybatis-config.xml파일 사용하는 경우
//        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
//
////2. mybatis-config.xml파일 대신 java로 설정하는 경우
////        org.apache.ibatis.session.Configuration configuration =
////                getConfiguration();
////        sessionFactory.setConfiguration(configuration);
//
////1)mapper용 xml에서 <insert>, <select>태그를 사용하려면 설정하세요
//// (1) 각각의 mapper용 xml
//// Resource[] mapperLocations = new Resource[] {
////                new ClassPathResource("mapper/ProductMapper.xml"),
////                new ClassPathResource("mapper/CustomerMapper.xml"),
////                new ClassPathResource("mapper/OrderMapper.xml")
////        };
////        sessionFactory.setMapperLocations(mapperLocations);
//
//// (2)모든 mapper용 xml
//        sessionFactory.setMapperLocations(new
//                PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
//        return sessionFactory.getObject();
//    }
//
//    private org.apache.ibatis.session.Configuration getConfiguration() {
//        org.apache.ibatis.session.Configuration configuration =
//                new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.getTypeAliasRegistry().registerAlias("Product", com.multi.spring2.product.vo.Product.class);
//        configuration.getTypeAliasRegistry().registerAlias("Customer", com.multi.spring2.customer.vo.Customer.class);
//        configuration.getTypeAliasRegistry().registerAlias("OrderInfo", com.multi.spring2.order.vo.OrderInfo.class);
//        configuration.getTypeAliasRegistry().registerAlias("OrderLine", com.multi.spring2.order.vo.OrderLine.class);
//
////        mapper용 interface에서 @Insert, @Select등의 어노테이션을 사용하려면 설정하세요
////        @Insert, @Select등의 어노테이션 사용을 안할거면 설정 안합니다
////        configuration.addMapper(BoardMapper.class);
//        return configuration;
//    }
//
//    /**
//     * SqlSessionFactory를 직접사용하면 트랜잭션 관리를 직접 구현해야 합니다.
//     * SqlSessionTemplate를 사용하면 SqlSessionFactory를 사용하여 생성된 SqlSession을 래핑하며, 트랜잭션 관리와 예외 처리를 자동으로 처리합니다.
//     *
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    /*<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
//    	<property name="dataSource" ref="dataSource"></property>
//    </bean>
//     */
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
//
//
