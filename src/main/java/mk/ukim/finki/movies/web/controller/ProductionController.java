package mk.ukim.finki.movies.web.controller;

import mk.ukim.finki.movies.service.ProductionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productions")
public class ProductionController {


    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public String getProductionsPage(Model model){
        model.addAttribute("productions", productionService.findAll());
        model.addAttribute("bodyContent", "listProductions");
        return "master-template";
    }

    @GetMapping("/add-form")
    public String getAddProductionPage(Model model){
        model.addAttribute("bodyContent", "add-production");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduction(@RequestParam String name,
                                 @RequestParam String country,
                                 @RequestParam String address){

        this.productionService.add(name, country, address);
        return "redirect:/productions";
    }

    @GetMapping("edit-form/{id}")
    public String getEditProductionForm(@PathVariable Long id, Model model){
        if(productionService.findById(id).isPresent()){
            model.addAttribute("production", productionService.findById(id).get());
            model.addAttribute("productions", this.productionService.findAll());
            model.addAttribute("bodyContent", "add-production");
            return "master-template";
        }
        return "redirect:/productions?error=ProductionNotFound";
    }

    @PostMapping("/edit/{productionId}")
    public String editProduction(@PathVariable Long productionId,
                                 @RequestParam String name,
                                 @RequestParam String country,
                                 @RequestParam String address){

        this.productionService.edit(productionId, name, country, address);
        return "redirect:/productions";
    }
}

