package com.education.project.cars.manager.carsmanager.utils;

import com.education.project.cars.manager.carsmanager.IOService.ReadServiceTestSampleImp;
import com.education.project.cars.manager.carsmanager.IOService.WriteServiceDBImp;
import com.education.project.cars.manager.carsmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;


//@Service
public class Main {
//    @Autowired
//    private CarListConstructor carListConstructor;

    @Autowired
    public DBPoolService source;

    //@Autowired
    //private CarServiceProcessorImp processorShowTest;

    @Autowired
    ReadServiceTestSampleImp testSampleIn;

    @Autowired
    WriteServiceDBImp postgresOut;


//    @PostConstruct
    public void run() {
        if (source.checkStatus()) {

            String currentTable = "Garage";
            System.out.println(1);
            source.removeDB(currentTable);
            System.out.println(2);
            source.createDB(currentTable);
            System.out.println(3);
            testSampleIn.carListRead("");
            System.out.println(4);
            postgresOut.carListWrite(
                    testSampleIn.carListRead(""), currentTable);
            System.out.println(5);




            //postgresOut.carWriter(new Car(), "Garage");

            /*
            //закинуть тестовую выборку в SQL
            processorShowTest = new CarServiceProcessorImp(
                    new ReadServiceTestSampleImp(),
                    new WriteServiceDBImp(source),
                    new CarServiceAltImp(),
                    "","Garage");
            processorShowTest.out(processorShowTest.in());
             */

//            CarServiceProcessorImp processor;
//            System.out.println("Stage 1");
//            processor = carListConstructor.construct(source, source);
            /*
            processor = new CarServiceProcessorImp(
                    new ReadServiceDBImp(),
                    new WriteServiceScreenImp(),
                    new CarServiceDBAltImp(source, "Garage"),
                    "Garage","");
             */

  //          if (processor != null) {
    //            dialogMenuService.commonActions(processor);
      //      }


        }
        else System.out.println("Postgres isn't run");
        source.closeConnection();
    }

}