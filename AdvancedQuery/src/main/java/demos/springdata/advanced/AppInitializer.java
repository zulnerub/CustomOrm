package demos.springdata.advanced;

import demos.springdata.advanced.dao.IngredientRepository;
import demos.springdata.advanced.dao.LabelRepository;
import demos.springdata.advanced.dao.ShampooRepository;
import demos.springdata.advanced.entity.Ingredient;
import demos.springdata.advanced.entity.Shampoo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements ApplicationRunner {
    private final ShampooRepository shampooRepo;
    private final LabelRepository labelRepo;
    private final IngredientRepository ingredientRepo;
    private final BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

    public AppInitializer(ShampooRepository shampooRepo, LabelRepository labelRepo, IngredientRepository ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.labelRepo = labelRepo;
        this.ingredientRepo = ingredientRepo;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //ex1
      /*  shampooRepo.findBySize(MEDIUM)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));
        */
        //ex2
       /* Size size = Size.valueOf(rd.readLine());
        Label label = labelRepo.findOneById((long) Integer.parseInt(rd.readLine()));
        shampooRepo.findBySizeOrLabelOrderByPriceAsc(size, label)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));
        */

        // Ex3
     /*   double price = Double.parseDouble(rd.readLine());
        shampooRepo.findByPriceIsGreaterThanOrderByPriceDesc(price)
                .forEach(s -> System.out.printf("%s %s %s %.2f%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice()));
      */
        //ex4
       /* String input = rd.readLine().trim();
        ingredientRepo.findByNameStartsWith(input)
                .forEach(i -> System.out.printf("%s%n", i.getName()));

        */
        //Ex 7

       /* shampooRepo.findWithIngredientsInList(
                List.of(ingredientRepo.findByName("Berry"), ingredientRepo.findByName("Mineral-Collagen")))
                .forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                        s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                        s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

        */

        //ex5
     /*   ingredientRepo.findByNameInOrderByPrice(List.of("Lavender", "Herbs", "Apple"))
                .forEach(i -> System.out.printf("%s %s %.2f%n",
                        i.getId(), i.getName(), i.getPrice()));

      */
     //ex8
       /* shampooRepo.findByCountOfIngredientsLowerThan(2)
                .forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

        */

       //10
      /*  ingredientRepo.updatePriceIngredientsInListBy10Percents(List.of("Lavender", "Herbs", "Apple"));

        ingredientRepo.findAll()
                .forEach(i -> System.out.printf("%s %s %.2f%n",
                        i.getId(), i.getName(), i.getPrice()));

       */
        //8. Print in pages of 5
        Page<Shampoo> page;
        Pageable pageable = PageRequest.of(0,5);
        do {
            page = shampooRepo.findAll(pageable);
            System.out.printf("Page %d of %d:%n------------------%n", page.getNumber()+1, page.getTotalPages());
            page.forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                    s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                    s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));
            pageable = pageable.next();
        } while(page.hasNext());

    }
}
