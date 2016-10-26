@Grab("org.grails:grails-datastore-gorm-hibernate4:3.1.4.RELEASE")
@Grab("org.grails:grails-spring:2.5.0")
@Grab('org.slf4j:slf4j-simple:1.7.12')

import grails.orm.bootstrap.*
import grails.persistence.*
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.h2.Driver
import groovy.sql.Sql
import us.codecraft.webmagic.*

println("More groovy...")

// def sql = Sql.newInstance("jdbc:h2:file:/Java/bwcrawler.db", "sa", "sa", "org.h2.Driver")

init = new HibernateDatastoreSpringInitializer(PersonW)
def dataSource = new DriverManagerDataSource(Driver.name, "jdbc:h2:file:~/bwcrawler.db;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE", 'sa', 'sa')
init.configureForDataSource(dataSource)

PersonW.create()

PersonW.withNewSession {
    PersonW.list()*.delete(flush:true)

    new PersonW(name:'tyama').save()
    new PersonW(name:'tama').save()

    println PersonW.count()
    println PersonW.list()*.name
}

PersonW.withNewSession {
    println "Total people = " + PersonW.count()
}

@Entity
class Category
{
    public String name, hash
    public int level  //0: Root category, 1: SubCategory, 2: SubSubCategory
    public String categoryName, subCategoryName, subSubCategoryName
    public Category parentCategory
}

@Entity
public class Product
{
    public String uniqueId, name
    public String url, urlDetail, urlDetailSpec
    public String imgSmall, imgMedium, imgLarge
    public BigDecimal pricefixed
    public boolean priceVariable
    public String priceTag
    public boolean isNewProduct
    public String brand, title, shortDescription, longDescription
    public String dataArtName, pdf, urlPrice
    public Category category
}

@Entity
public class Variant
{
    Product product
    public String artNumber, description, metas
    public BigDecimal varPrice
}

@Entity
public class WebPage
{
    public String pagePath, content
}

def createTables() {
    WebPage.init
}

def RunCrawler(Sql sql, String webpage) {

}
