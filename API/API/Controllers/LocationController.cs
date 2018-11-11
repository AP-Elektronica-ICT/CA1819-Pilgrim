using BusinessLayer;
using DataLinkLayer.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Controllers
{
    [Route("api/locations")]
    public class LocationController : Controller
    {
        private readonly LocationService locationService;

        public LocationController(LocationService locationService)
        {
            this.locationService = locationService;
        }

        [HttpGet]
        public IActionResult GetLocations()
        {
            return Ok(locationService.GetLocations());
        }

        [HttpGet("{id}")]
        public IActionResult GetLocation(int id)
        {
            Location location = locationService.GetLocation(id);
            if (location == null)
                return NotFound();
            return Ok(location);
        }

        [HttpPost]
        public IActionResult newLocation([FromBody] Location newLocation)
        {
            Location addedLocation = locationService.AddLocation(newLocation);
            if (addedLocation == null)
                return BadRequest();
            return Ok(newLocation);
        }

        [HttpDelete("{id}")]
        public IActionResult deleteLocation(int id)
        {
            if (locationService.DeleteLocation(id) == false)
                return NotFound();
            return Ok();
        }
    }
}
