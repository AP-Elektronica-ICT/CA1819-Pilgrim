using BusinessLayer;
using DataLinkLayer.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Controllers
{

    [Route("api/pilgrimages")]
    [ApiController]
    public class PilgrimageController : Controller
    {
        private readonly PilgrimageService pilgrimageService;

        public PilgrimageController(PilgrimageService pilgrimageService)
        {
            this.pilgrimageService = pilgrimageService;
        }

        [HttpGet]
        public IActionResult GetPilgrimages()
        {
            return Ok(pilgrimageService.GetLeaderboard(1,1,1));
        }

        [HttpGet("{id}")]
        public IActionResult GetPilgrimage(int id)
        {
            Pilgrimage pilgrimage = pilgrimageService.GetPilgrimage(id);
            if (pilgrimage == null)
                return NotFound();
            return Ok(pilgrimage);
        }

        [HttpPost]
        public IActionResult newPilgrimage([FromBody] Pilgrimage newPilgrimage)
        {
            Pilgrimage addedPilgrimage = pilgrimageService.AddPilgrimage(newPilgrimage);
            if (addedPilgrimage == null)
                return NotFound();
            return Ok(newPilgrimage);
        }

        [HttpDelete("{id}")]
        public IActionResult deletePilgrimage(int id)
        {
            if (pilgrimageService.DeletePilgrimage(id) == false)
                return NotFound();
            return Ok();
        }

        [HttpPut]
        public IActionResult UpdatePilgriamge([FromBody] Pilgrimage updatedPilgrimage)
        {
            if (this.pilgrimageService.UpdatePilgrimage(updatedPilgrimage) == false)
                return NotFound();
            return Ok(updatedPilgrimage);
        }

    }
}
