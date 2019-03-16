package com.uniovi.tests;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//Ordenamos las pruebas por el nombre del método

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyWallapopTest {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox64 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver022 = "C:\\Users\\Christian\\Desktop\\Labs\\SDI\\Selenium\\PL-SDI-Sesión5-material\\PL-SDI-Sesion5-material\\geckodriver022win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox64, Geckdriver022);

	static String URLlocal = "http://localhost:8090";
	static String URLremota = "http://urlsdispring:xxxx";
	static String URL = URLlocal; // Se va a probar con la URL remota, sino URL=URLlocal

	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private UsersRepository usersRepository;

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		initdb();
		driver.navigate().to(URL);
	}

	public void initdb() {
		// Borramos todas las entidades.
		usersRepository.deleteAll();

		User user1 = new User("christian@email.com", "Christian", "Peláez");
		user1.setPassword("123456");
		user1.setPasswordConfirm("123456");
		user1.setRole(rolesService.getRoles()[0]);

		User user2 = new User("cristina@email.com", "Cristina", "Ruíz");
		user2.setPassword("123456");
		user2.setPasswordConfirm("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("gema@email.com", "Gema", "Rico");
		user3.setPassword("123456");
		user3.setPasswordConfirm("123456");
		user3.setRole(rolesService.getRoles()[0]);

		User user4 = new User("noe@gmail.com", "Noe", "Fernandez");
		user4.setPassword("123456");
		user4.setPasswordConfirm("123456");

		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("victoria@gmail.com", "Victoria", "Salinas");
		user5.setPassword("123456");
		user5.setPasswordConfirm("123456");
		user5.setRole(rolesService.getRoles()[0]);

		User user6 = new User("admin@email.com", "Edward", "Núñez");
		user6.setPassword("admin");
		user6.setPasswordConfirm("admin");
		user6.setRole(rolesService.getRoles()[1]);

		Set user1Marks = new HashSet<Offer>() {
			{
				Offer oferta = new Offer("A1", "Oferta A1", 10.0, user1);
				oferta.setPurchased(true);
				add(oferta);
				add(new Offer("A2", "Oferta A2", 9.0, user1));
				add(new Offer("A3", "Oferta A3", 7.0, user1));
				add(new Offer("A4", "Oferta A4", 6.5, user1));
			}
		};
		user1.setOffers(user1Marks);
		Set user2Marks = new HashSet<Offer>() {
			{
				add(new Offer("B1", "Oferta B1", 5.0, user2));
				add(new Offer("B2", "Oferta B2", 4.3, user2));
				add(new Offer("B3", "Oferta B3", 8.0, user2));
				add(new Offer("B4", "Oferta B4", 3.5, user2));
			}
		};
		user2.setOffers(user2Marks);
		Set user3Marks = new HashSet<Offer>() {
			{
				;
				add(new Offer("C1", "Oferta C1", 5.5, user3));
				add(new Offer("C2", "Oferta C2", 6.6, user3));
				add(new Offer("C3", "Oferta C3", 7.0, user3));
			}
		};
		user3.setOffers(user3Marks);
		Set user4Marks = new HashSet<Offer>() {
			{
				add(new Offer("D1", "Oferta D1", 10.0, user4));
				add(new Offer("D2", "Oferta D2", 8.0, user4));
				add(new Offer("D3", "Oferta D3", 9.0, user4));
			}
		};

		user4.setOffers(user4Marks);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR1_1:Registro de Usuario con datos válidos.
	@Test
	public void PR1_1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "77777", "77777");
		
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "id", "profile_title");
	}

	// PR1_2:Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void PR1_2() {
//		// Vamos al formulario de registro
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		
//		// Rellenamos el formulario con email vacío
//		PO_RegisterView.fillForm(driver, "", "Josefo", "Perez", "77777", "77777");
//		// COmprobamos el error del email vacío.
//		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		fail();
	}
	
	// PR1_3: Registro de Usuario con datos inválidos (repetición de contraseña inválida)
		@Test
		public void PR1_3() {
			// Vamos al formulario de registro
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			// Rellenamos el formulario con contraseñas que no coinciden
			PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "123456", "654321");
			// COmprobamos el error de las 2 contraseñas diferentes
			PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());

		}

		@Test
		public void PR1_4() {
			// Vamos al formulario de registro
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			// Rellenamos el formulario 
			PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "123456", "123456");
			
			//Pinchamos en la opción de opciones de perfil: //li[contains(@id, 'perfil-menu')]/a
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'perfil-menu')]/a");
			elementos.get(0).click();
			
			//Esperamos a aparezca la opción de desconectar: //a[contains(@id, 'btn_logOut')]
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@id, 'btn_logOut')]");
			//Pinchamos Desconectar
			elementos.get(0).click();
			
			// Vamos al formulario de registro
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			// Rellenamos el formulario con el mismo usuario
			PO_RegisterView.fillForm(driver, "josefo@email.com", "Josefo", "Perez", "123456", "123456");
			
			// COmprobamos el error de las 2 contraseñas diferentes
			PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());

		}
		
}
