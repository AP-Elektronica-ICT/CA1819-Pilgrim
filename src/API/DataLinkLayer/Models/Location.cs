﻿using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLinkLayer.Models
{
    public class Location
    {
        public int ID { get; set; }
        public string Naam { get; set; }
        public string Description { get; set; }
        public double? Lat { get; set; }
        public double? Long { get; set; }
        public string CrypticClue { get; set; }
        public string Hint1 { get; set; }
        public string Hint2 { get; set; }
        public string Answer { get; set; }
        public string base64 { get; set; }
        [JsonIgnore]
        public byte[] Image { get; set; }

        [JsonIgnore]
        public ICollection<CollectionLocation> collectionLocations { get; set; }

    }
}
