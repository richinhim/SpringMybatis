# SpringMybatis
spring/Mybatis multi datasource

스프링에서 Mybatis multi-datasource 연동법입니다.



안녕하십니까? 지난주 spring4 + mybatis3.3 + dbcp를 이용하여 

다중 데이터 소스 연결하는 부분을 해결한 내용을 올려드립니다.

spring version : 4.2.0

mybatis version : 3.3.0

mybatis-spring version : 1.2.3

WAS : wildfly9.0.1.Final

database : mariaDB10.0 

을 이용하였습니다.

그리고 소스는 DAO를 사용하지 않고 mapper 와 service를 사용하였습니다.




에러 내용의 핵심은 2가지로 요약됩니다.

1.

org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type [com.proto.net.aron.aronMapper] found for dependency: expected at least 1 bean which qualifies as autowire candidate for this dependency. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true) , @org.springframework.beans.factory.annotation.Qualifier(value=Mapper)}  




2.

MyBatis 의 MapperScannerConfigurer 가 어떤  SqlSessionFactory 를 참조해야 할지 몰라서 발생한다.


org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [org.apache.ibatis.session.SqlSessionFactory] is defined: expected single matching bean but found 2: sqlSessionFactory,sqlSessionFactory_mose  








아래 부분은 application-config.xml 부분입니다.

   <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">

    	<property name="driverClassName" value="${database.aron.driverClassName}"/>

    	<property name="url" value="${database.aron.url}"/>

    	<property name="username" value="${database.aron.username}"/>

    	<property name="password" value="${database.aron.password}"/>

    	<property name="testOnBorrow" value="true"/>

        <property name="testOnReturn" value="true"/>

        <property name="testWhileIdle" value="true"/>

        <property name="timeBetweenEvictionRunsMillis" value="1800000"/>

        <property name="numTestsPerEvictionRun" value="3"/>

        <property name="minEvictableIdleTimeMillis" value="1800000"/>

        <property name="validationQuery" value="SELECT 1"/>

    </bean>

    

	<context:annotation-config/>

	

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">

  		<property name="dataSource" 		ref="dataSource" />

  		<property name="configLocation" 	value="classpath:mybatis/mybatis-config.xml"/>

  		<property name="mapperLocations">

  			<list>

  				<value>classpath:mybatis/mappers/aron/aron.xml</value>

  				

  			</list>

  		</property>

  		

  	</bean>

	

	<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate" destroy-method="clearCache">

		<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory" />

	</bean>

	

	<!-- mybatis mapper auto scanning -->

	<bean id="dsAronScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">

		<property name="basePackage" value="com.proto.net.aron.mapper" />

		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>

		

	</bean>

	

	

	<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true" />

	

	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">

  		<property name="dataSource" ref="dataSource" />

	</bean>

    

    

    

     <bean id="dataSource_mose" class="org.apache.commons.dbcp.BasicDataSource">

    	<property name="driverClassName" value="${database.mose.driverClassName}"/>

    	<property name="url" value="${database.mose.url}"/>

    	<property name="username" value="${database.mose.username}"/>

    	<property name="password" value="${database.mose.password}"/>

	    <property name="testOnBorrow" value="true"/>

        <property name="testOnReturn" value="true"/>

        <property name="testWhileIdle" value="true"/>

        <property name="timeBetweenEvictionRunsMillis" value="1800000"/>

        <property name="numTestsPerEvictionRun" value="3"/>

        <property name="minEvictableIdleTimeMillis" value="1800000"/>

        <property name="validationQuery" value="SELECT 1"/>

    </bean>

    

    <bean id="sqlSessionFactory_mose" class="org.mybatis.spring.SqlSessionFactoryBean">

  		<property name="dataSource" 		ref="dataSource_mose" />

  		<property name="configLocation" 	value="classpath:mybatis/mybatis-config.xml"/>

  		<property name="mapperLocations">

  			<list>

  				<value>classpath:mybatis/mappers/mose/mose.xml</value>

  			</list>

  		</property>

  		

  	</bean>

	

	<bean id="sqlSessionTemplate_mose" class="org.mybatis.spring.SqlSessionTemplate" destroy-method="clearCache">

		<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory_mose" />

	</bean>

	

	<!-- mybatis mapper auto scanning -->

	<bean id="dsMoseScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">

		<property name="basePackage" value="com.proto.net.mose.mapper" />

		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory_mose"/>

	</bean>

	

	

	<bean id="transactionManager_mose" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">

  		<property name="dataSource" ref="dataSource_mose" />

	</bean>

	<tx:annotation-driven transaction-manager="transactionManager_mose" proxy-target-class="true" />

	

	<context:component-scan base-package="com.proto.net.aron.service,com.proto.net.mose.service" />




1번 오류에 대한 해결책은 mapper inteface 상단에 

@Resource(name = "dsMoseScanner") 를 추가해 주는 것입니다.




2번 오류의 해결책은 아래와 같이 매퍼가 사용할 sqlSessionFactory를 지정해 주는 것입니다.

<bean id="dsMoseScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">

		<property name="basePackage" value="com.proto.net.mose.mapper" />

		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory_mose"/>

</bean> 




감사합니다.



1번과 2번의 설정을 제거하고 한방에 처리 하는 방법을 찾았습니다.

application-config.xml 파일에 

<mybatis:scan base-package=""com.proto.net.mose.mapper" factory-ref="SqlSessionFactory_mose"  />

<mybatis:scan base-package=""com.proto.net.aron.mapper" factory-ref="SqlSessionFactory_aron"  />

으로 정의하게 되면 

<context:component-scan base-package="com.proto.net.aron.service,com.proto.net.mose.service" /> 이부분과 




<!-- mybatis mapper auto scanning -->

	<bean id="dsMoseScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">

		<property name="basePackage" value="com.proto.net.mose.mapper" />

		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory_mose"/>

	</bean>




<!-- mybatis mapper auto scanning -->

	<bean id="dsAronScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">

		<property name="basePackage" value="com.proto.net.aron.mapper" />

		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory_aron"/>

	</bean>

도 제거할수 있습니다.

그리고

mapper inteface 상단에 

@Resource(name = "dsMoseScanner") 를 추가해 주지 않아도 됩니다.

감사합니다.


        
