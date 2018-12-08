using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class Pilgrimage
    {
        public int ID { get; set; }

        public int? userID { get; set; }



        public DateTime StartTime { get; set; }

        public int Time { get; set; }

        

        //10 locations per pilgrimage?
        public ICollection<Location> Locations { get; set; }

        public Profile profile { get; set; }

        [JsonIgnore]
        public int profileID { get; set; }
    }
}
