package alararestaurant.service;

import alararestaurant.domain.dtos.OrderSeedRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static alararestaurant.constants.GlobalConstants.ORDERS_FILE_PATH;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final EmployeeService employeeService;
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, FileUtil fileUtil, EmployeeService employeeService, ItemService itemService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        OrderSeedRootDto orderSeedRootDto = this.xmlParser
                .importFromXml(OrderSeedRootDto.class, ORDERS_FILE_PATH);

        System.out.println();

        orderSeedRootDto.getOrders()
                .forEach(e -> {
                    if (this.validationUtil.isValid(e)) {
                        Employee employee = this.employeeService.getByName(e.getEmployee());
                        if (employee != null) {
                            Order ord = this.modelMapper.map(e, Order.class);
                            ord.setEmployee(employee);

                            long initialCount = e.getOrderItems().getOrderItems().size();
                            long actualCount = e.getOrderItems().getOrderItems()
                                    .stream()
                                    .filter(i -> this.itemService.getByName(i.getName()) != null
                                            && this.validationUtil.isValid(i))
                                    .count();

                            if (initialCount == actualCount) {
                                e.getOrderItems().getOrderItems()
                                        .forEach(orIt -> {
                                            System.out.println();

                                            ord.addOrderItem(new OrderItem(this.itemService.getByName(orIt.getName()), orIt.getQuantity()));
                                            ord.setEmployee(employee);

                                            this.orderRepository.save(ord);
                                        });


                            } else {
                                sb.append("Error: Invalid data.");
                            }
                        } else {
                            sb.append("Error: Invalid data.");
                        }
                    } else {
                        sb.append("Error: Invalid data.");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}
