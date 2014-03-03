import groovy.text.*
import groovy.xml.MarkupBuilder;

def text='''
package beans;
import org.springframework.stereotype.Component;
@Component
public class Bean${id}
{
	String name;
}
'''

int number = 100
def engine = new SimpleTemplateEngine()
def template = engine.createTemplate(text)

(1..number).each {
	def binding = [id: it]
	def contents = template.make(binding).toString()
	def file = new File("src/beans/Bean${it}.java")
	file.text = contents
}

new File("src/startup/applicationContext.xml").withWriter { 
	w ->
	MarkupBuilder builder = new MarkupBuilder(w)
	builder.beans (xmlns: "http://www.springframework.org/schema/beans",
				    'xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
				   'xsi:schemaLocation': "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd") {
		(1..number).each {
			bean id: "bean$it", 'class': "beans.Bean$it"
		}
	}
}


// Generate Java config
text='''
package startup;
import org.springframework.context.annotation.*;
import beans.*;
@Configuration
public class BeanConfig
{'''
(1..number).each {
	id ->
	def beanMeth="""
	@Bean
	public Bean$id getBean$id()
	{
		return new Bean$id();
	}
"""		
	text += beanMeth
}
text += '}'

new File("src/startup/BeanConfig.java").text = text

println 'done'
