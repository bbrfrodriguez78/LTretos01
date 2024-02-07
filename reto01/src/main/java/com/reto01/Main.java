/* CENTRO DE ESTUDIOS: LITE THINKING
   ASIGNATURA        : MÁSTER EN QA Y AUTOMATIZACIÓN DE PRUEBAS
   PROFESOR          : DAVID PEÑA CUELLAR
   ESTUDIANTE        : RONALD RODRIGUEZ CASTRO 
   REQUERIMIENTO     :
   Ingresar a la tienda URL: https://demoblaze.com/ y confirmar el valor de los siguientes productos:
    -Phones
        HTC ONE M9
        MacBook air
    -Laptop
        MacBook Pro
        Dell i7 8gb
    -Monitors
        ASUS Full HD
 */
package com.reto01;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Articulo {  
    String categoria, articulo, valor;  
    public Articulo(String categoria, String articulo, String valor) {  
        this.categoria = categoria;  
        this.articulo = articulo;  
        this.valor = valor;  
        }  
    }  


public class Main {
    // Método para desplazarse hasta un elemento
    private static void scrollIntoView(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void main(String[] args) {
        //Crea lista of Articulos  
        List<Articulo> list=new ArrayList<Articulo>();  
        
        //Creando articulo  
        Articulo item1 = new Articulo("Phones", "HTC One M9", "$700");
        Articulo item2 = new Articulo("Laptops", "MacBook air", "$700");
        Articulo item3 = new Articulo("Laptops", "MacBook Pro", "$1100");
        Articulo item4 = new Articulo("Laptops", "Dell i7 8gb", "$700");
        Articulo item5 = new Articulo("Monitors", "ASUS Full HD", "$230");
                
        //Agregando articulos a la listadding Books to list  
        list.add(item1);  
        list.add(item2); 
        list.add(item3); 
        list.add(item4); 
        list.add(item5); 
        
        //Definir driver + abrir sitio + maximizar navegador  
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://demoblaze.com/");
        driver.manage().window().maximize();
        
        //Declaración de elementos
        List<WebElement> grupoMenu = driver.findElements(By.id("itemc")); //Se identifica por id, encontrando que pertenece a un grupo
        WebElement categoriaItemMenu = null;
        WebElement productoSeleccionado = null;  
        WebElement precio = null;
        
        for (int i = 0; i < grupoMenu.size(); i++) {//Recorre el menú
            categoriaItemMenu = driver.findElement(By.xpath("//a[text()='"+grupoMenu.get(i).getText()+"']"));
            scrollIntoView(driver, categoriaItemMenu); //Posicionamiento con scroll para encontrar el elemento visualmente
            System.out.println("Categoria:  "+categoriaItemMenu.getText());
            categoriaItemMenu.click();
            
            for(Articulo itemArt:list){ //Busca el artículo conforme a la lista de elementos
                if(itemArt.categoria.equals(grupoMenu.get(i).getText())){//Valida categoria (Lista vs Elemento para valor de Item de Menú)
                    productoSeleccionado=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+itemArt.articulo+"']")));//Localiza Producto
                    System.out.println("Producto:   "+productoSeleccionado.getText());
                    productoSeleccionado.click();//Accede al detalle del Producto
                    
                    precio = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("price-container")));//Localiza en el detalle el precio
                    if(precio.getText().contains(itemArt.valor)){//Punto de verificación (Lista vs Elemento Precio)
                        System.out.println("Precio:     "+precio.getText());
                        driver.navigate().back();//Retorno de navegación
                    }else{
                        System.out.println("ARTICULO NO ENCONTRADO");//Mensaje cuando no encuentra el articulo
                    }
                }else{
                    continue;
                }
            }
        }
        driver.quit();
    }
}