using BusinessLayer;
using DataLinkLayer.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Controllers
{
    [Route("api/profiles")]
    public class ProfileController : Controller
    {
        private readonly ProfileService profileService;

        public ProfileController(ProfileService profileService)
        {
            this.profileService = profileService;
        }

        [HttpGet]
        public IActionResult GetProfiles()
        {
            return Ok(profileService.GetProfiles());
        }

        [HttpPost]
        public IActionResult Profile([FromBody] Profile newProfile)
        {
            Profile profile = profileService.AddProfile(newProfile);
            if (profile == null)
                return NotFound();
            return Ok(newProfile);
        }


        [HttpGet("{id}")]
        public IActionResult GetProfile(string id)
        {
            Profile profile = profileService.GetProfileFireBaseID(id);
            if (profile == null)
                return NotFound();
            return Ok(profile);
        }



        [HttpDelete("{id}")]
        //Geen idee of er post/delete nodig is? Nieuwe accounts worden aangemaakt via
        //FireBase en acc's verwijderen doen wij enkel
        public IActionResult DeleteProfile(string id)
        {
            bool deleted = profileService.deleteProfile(id);
            if (deleted)
                return NoContent();

            return NotFound();
        
        }



        [HttpPut]
        public IActionResult UpdateProfile([FromBody] Profile updatedProfile)
        {
            if (this.profileService.UpdateProfile(updatedProfile) == false)
                return NotFound();
            return Ok(updatedProfile);
        }

    }
}
