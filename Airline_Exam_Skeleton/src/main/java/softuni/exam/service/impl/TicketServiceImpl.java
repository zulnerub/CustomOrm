package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.TicketRootSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.exam.constants.GlobalConstants.TICKETS_FILE_PATH;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownServiceImpl townService;
    private final PassengerServiceImpl passengerService;
    private final PlaneServiceImpl planeService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, TownServiceImpl townService, PassengerServiceImpl passengerService, PlaneServiceImpl planeService) {
        this.ticketRepository = ticketRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.passengerService = passengerService;
        this.planeService = planeService;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return this.fileUtil.readFile(TICKETS_FILE_PATH);
    }

    @Override
    public String importTickets() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        System.out.println();
        TicketRootSeedDto ticketRootSeedDto =
                this.xmlParser.importFromXml(TicketRootSeedDto.class, TICKETS_FILE_PATH);
        System.out.println();

        ticketRootSeedDto.getTicketSeedDtoList()
                .forEach(t -> {
                    if (this.validationUtil.isValid(t)){
                        Ticket ticket = this.modelMapper.map(t, Ticket.class);
                        if (this.ticketRepository.findBySerialNumber(t.getSerialNumber()).orElse(null) == null){
                            Town fromTown = this.townService.getTownByName(t.getFromTown().getName()).orElse(null);
                            Town toTown = this.townService.getTownByName(t.getToTown().getName()).orElse(null);
                            Passenger passenger = this.passengerService.getPassengerByEmail(t.getPassenger().getEmail()).orElse(null);
                            Plane plane = this.planeService.getPlaneByRegisterNumber(t.getPlane().getRegisterNumber()).orElse(null);

                            if (fromTown != null && toTown != null && passenger != null && plane != null){
                                System.out.println();
                                ticket.setFromTown(fromTown);
                                ticket.setToTown(toTown);
                                ticket.setPassenger(passenger);
                                ticket.setPlane(plane);
                                System.out.println();

                                sb.append(String.format("Successfully imported %s %s - %s",
                                        ticket.getClass().getSimpleName(), ticket.getFromTown().getName(), ticket.getToTown().getName()));

                                this.ticketRepository.saveAndFlush(ticket);

                            }else {
                                sb.append("Invalid Ticket");
                            }
                        }else {
                            sb.append("Invalid Ticket");
                        }
                    }else {
                        sb.append("Invalid Ticket");
                    }
                    sb.append(System.lineSeparator());
                });




        return sb.toString();
    }
}
