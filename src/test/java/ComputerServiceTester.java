import appPackage.data.Brand;
import appPackage.data.Computer;
import appPackage.repositories.ComputerRepository;
import appPackage.services.implementations.ComputerServiceImpl;
import appPackage.services.interfaces.ComputerService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceTester {

    private static List<Computer> allComputers;
    private static Computer dell;
    private static Computer apple;
    private static Computer samsung;
    private static Computer toshiba;

    @Mock
    private ComputerRepository computerRepository;
    @InjectMocks
    private ComputerService computerService = new ComputerServiceImpl();

    @BeforeClass
    public static void createHypotheticalComputerLists(){
        dell = new Computer("123", Brand.DELL, "dell info");
        apple = new Computer("456", Brand.APPLE, "apple info");
        samsung = new Computer("789", Brand.SAMSUNG, "samsung info");
        toshiba = new Computer("127", Brand.TOSHIBA, "toshiba info");
        dell.setNeedsReparation(true);
        apple.setNeedsReparation(false);
        samsung.setNeedsReparation(false);
        toshiba.setNeedsReparation(true);

    }

    @Test
    public void testIfYouGetBrokenComputers(){
        List<Computer> allBrokenComputers = new ArrayList<>();
        allBrokenComputers.add(dell);
        allBrokenComputers.add(toshiba);
        when(computerRepository.findByNeedsReparationTrue()).thenReturn(allBrokenComputers);
        List<Computer> foundBrokenComputers = computerService.getAllBrokenComputers();
        Assert.assertTrue(foundBrokenComputers.contains(dell));
        Assert.assertTrue(foundBrokenComputers.contains(toshiba));
        Assert.assertFalse(foundBrokenComputers.contains(apple));
        Assert.assertFalse(foundBrokenComputers.contains(samsung));
        Assert.assertEquals(2, foundBrokenComputers.size());

    }

    @Test
    public void testIfYouGetFixedComputers(){
        List<Computer> allWorkingComputers = new ArrayList<>();
        allWorkingComputers.add(apple);
        allWorkingComputers.add(samsung);
        when(computerRepository.findByNeedsReparationFalse()).thenReturn(allWorkingComputers);
        List<Computer> foundWorkingComputers = computerService.getAllWorkingComputers();
        Assert.assertEquals(2, foundWorkingComputers.size());
        Assert.assertTrue(foundWorkingComputers.contains(apple));
        Assert.assertTrue(foundWorkingComputers.contains(samsung));
        Assert.assertFalse(foundWorkingComputers.contains(dell));
        Assert.assertFalse(foundWorkingComputers.contains(toshiba));

    }
}